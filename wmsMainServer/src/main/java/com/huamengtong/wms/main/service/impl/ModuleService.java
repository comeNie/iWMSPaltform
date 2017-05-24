package com.huamengtong.wms.main.service.impl;

import com.huamengtong.wms.constants.GlobalConstants;
import com.huamengtong.wms.convert.ListArraysConvert;
import com.huamengtong.wms.core.formwork.db.domain.CurrentUserEntity;
import com.huamengtong.wms.core.web.MessageResult;
import com.huamengtong.wms.dto.TWmsModuleDTO;
import com.huamengtong.wms.dto.TWmsPermissionDTO;
import com.huamengtong.wms.entity.MenuTreeNode;
import com.huamengtong.wms.entity.TreeNode;
import com.huamengtong.wms.entity.main.TWmsModuleEntity;
import com.huamengtong.wms.entity.main.TWmsPermissionEntity;
import com.huamengtong.wms.enums.ModuleTypeEnum;
import com.huamengtong.wms.main.mapper.ModuleMapper;
import com.huamengtong.wms.main.service.IModuleService;
import com.huamengtong.wms.main.service.IPermissionService;
import com.huamengtong.wms.utils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ModuleService implements IModuleService {

    @Autowired
    ModuleMapper moduleMapper;

    @Autowired
    IPermissionService permissionService;

    /***
     * 根据当前用户获取菜单
     * @param sessionCurrentUser
     * @return
     */
    @Override
    public List getModulesByUser(CurrentUserEntity sessionCurrentUser) {
        List <TWmsModuleEntity> list = null;
        List <MenuTreeNode> retList = new ArrayList<MenuTreeNode>();
        if(sessionCurrentUser.getIsAdmin() != null && sessionCurrentUser.getIsAdmin() == 1){
            //如果是管理员获得所有模块菜单
            list = moduleMapper.selectAllModulesAdmin(ModuleTypeEnum.WEB.toString());
        }else{
            //如果是普通用户则根据登录用户权限获取模块
            list = moduleMapper.selectAllModuleNormal(sessionCurrentUser.getId(),ModuleTypeEnum.WEB.toString());
        }
        // 组合成树状
        list.forEach( x->{
            if(x.getParentId() == 0){
                MenuTreeNode menuTreeNode = new MenuTreeNode();
                copyEntity2Node(menuTreeNode,x);
                retList.add(menuTreeNode);
            }
        });

        for(MenuTreeNode menuTreeNode:retList){
            makeMenuTree(menuTreeNode,list);
        }
        return retList;
    }

    /***
     * 根据当前用户获取操作权限
     * @param sessionCurrentUser
     * @return
     */
    @Override
    public Map getAllModuleActionByUser(CurrentUserEntity sessionCurrentUser) {
        Map retMap = new HashMap();
        if( 1 == sessionCurrentUser.getIsAdmin()){
            retMap.put("isAdmin",true);
            return retMap;
        }
        //根据当前用户Id获取用户的模块及模块对应操作权限
        List<Map<String, Object>> mapList = permissionService.findPermissionByUserId(sessionCurrentUser.getId());
        List<Object[]> list = ListArraysConvert.listMapToArrayConvert(mapList);
        //获取模块Id
        List moduleIdList = new ArrayList();
        list.forEach(x-> {
            moduleIdList.add(x[0]);
        });
        if(moduleIdList.size() == 0){
            retMap.put("isAdmin",false);
            return retMap;
        }
        //过滤
        List<TWmsModuleEntity> mList = moduleMapper.selectModuleByIds(moduleIdList);
        list.forEach(x-> {
            Optional<TWmsModuleEntity> optional= mList.stream().filter(y->y.getId() == (Long)x[0]).findFirst();
            if(optional.isPresent()){
                x[0]=optional.get().getModulePath();
            }
        });

        Map map = list.stream().collect(Collectors.groupingBy(x->x[0],Collectors.toList()));
        Map permMap = new HashMap();
        for (Object key : map.keySet()) {
            List <Object[]>tempList = (List) map.get(key);
            List buttonList = new ArrayList();
            tempList.forEach(x->{
                Map buttonMap = new HashMap();
                buttonMap.put("buttonId",x[2]);
                buttonMap.put("buttonName",x[1]);
                buttonList.add(buttonMap);
            });
            permMap.put(key,buttonList);
        }
        retMap.put("isAdmin",false);
        retMap.put("perm",permMap);
        return retMap;
    }

    @Override
    public Map queryAllMenus() {
        //查询所有模块
        List<TWmsModuleEntity> moduleEntityList = moduleMapper.selectAllModulesAdmin(ModuleTypeEnum.WEB.toString());
        List<TreeNode> treeNodes = conventTreeNodeListFromModuleList(moduleEntityList,false,true);
        TreeNode treeNode = new TreeNode();
        treeNode.setId("0");
        treeNode.setParentId(-1);
        treeNode.setName("");
        treeNodes.add(treeNode);
        Map resultMap = new HashMap();
        resultMap.put("rows",treeNodes);
        return resultMap;
    }


    private List<TreeNode> conventTreeNodeListFromModuleList(List<TWmsModuleEntity> moduleEntityList, boolean isUrl, boolean isClick){
        if(CollectionUtils.isNotEmpty(moduleEntityList)){
            List<TreeNode> treeNodes = new ArrayList<TreeNode>();
            for(TWmsModuleEntity module : moduleEntityList){
                treeNodes.add(getTreeNodeFromModule(module,isUrl,isClick));
            }
            return treeNodes;
        }
        return null;
    }

    private TreeNode getTreeNodeFromModule(TWmsModuleEntity module, boolean isUrl, boolean isClick){
        TreeNode node = new TreeNode();
        node.setId(module.getId()+"");
        node.setName(module.getModuleName());
        node.setParentId(module.getParentId());
        if(isUrl){
            node.setUrl(module.getModulePath());
            if(!node.getUrl().equals("")){
                node.setUrl(addMoudleId2Url(node.getUrl(),node.getId()));
            }
        }
        if(isClick){
            node.setClick("treeNodeClick('"+module.getId()+"')");
        }
        node.setSortindex(module.getPosition());
        return node;
    }

    private String addMoudleId2Url(String url,String moudleId){
        if(url.contains("?")){
            url += "&";
        }else{
            url +="?";
        }
        url += "moudleId="+ moudleId;
        return url;
    }

    private void copyEntity2Node(MenuTreeNode menuTreeNode,TWmsModuleEntity moduleEntity){
        menuTreeNode.setId(String.valueOf(moduleEntity.getId()));
        menuTreeNode.setName(moduleEntity.getModuleName());
        menuTreeNode.setParentId(String.valueOf(moduleEntity.getParentId()));
        menuTreeNode.setPath(moduleEntity.getModulePath());
        menuTreeNode.setIcons(moduleEntity.getIcons());
    }

    private void makeMenuTree(MenuTreeNode menuTreeNode,List<TWmsModuleEntity> moduleList){
        List<MenuTreeNode> list = new ArrayList<MenuTreeNode>();
        moduleList.forEach(x->{
            if(menuTreeNode.getId().equals(String.valueOf(x.getParentId()))){
                MenuTreeNode menuTreeNodeSon = new MenuTreeNode();
                copyEntity2Node(menuTreeNodeSon,x);
                makeMenuTree(menuTreeNodeSon,moduleList);
                list.add(menuTreeNodeSon);
            }
        });
        menuTreeNode.setChildren(list);
    }


    @Override
    public MessageResult removeByPrimaryKey(Long id) {
        //TODO 删除前验证是否存在子级菜单
        moduleMapper.deleteByPrimaryKey(id);
        return MessageResult.getSucMessage();
    }

    @Override
    public TWmsModuleEntity findByPrimaryKey(Long id) {
        return  moduleMapper.selectByPrimaryKey(id);
    }

    @Override
    public Map queryModuleActions(Map searchMap) {
        Long moduleId = MapUtils.getLong(searchMap,"moduleId");
        List<TWmsPermissionEntity> permissionEntityList = permissionService.findPermissionByModuleId(moduleId);
        if(CollectionUtils.isNotEmpty(permissionEntityList)) {
            Map resultMap = new HashMap();
            resultMap.put("rows", permissionEntityList);
            resultMap.put("total", permissionEntityList.size());
            return resultMap;
        }
        return null;
    }

    @Override
    public MessageResult createModuleActions(TWmsPermissionDTO permissionDTO) {
        return permissionService.createPermission(permissionDTO);
    }

    @Override
    public MessageResult modifyModuleActions(TWmsPermissionDTO permissionDTO) {
        return permissionService.modifyPermissionEntity(permissionDTO);
    }

    @Override
    public MessageResult removeModuleActions(Long id) {
        return permissionService.removeByPrimaryKey(id);
    }

    @Override
    public MessageResult createModule(TWmsModuleDTO moduleDTO) {
        TWmsModuleEntity moduleEntity = BeanUtils.copyBeanPropertyUtils(moduleDTO, TWmsModuleEntity.class);
        moduleEntity.setTypeCode("WMS");
        moduleEntity.setIsVisible(true);
        if(StringUtils.isEmpty(moduleDTO.getIcons())) {
            moduleEntity.setIcons(GlobalConstants.DEFAULT_MENU_ICONS);
        }
        if (StringUtils.isEmpty(moduleEntity.getModuleType())) {
            moduleEntity.setModuleType("Web");
        }
        moduleMapper.insertModule(moduleEntity);
        return MessageResult.getSucMessage();
    }

    @Override
    public MessageResult modifyModule(Map map) {
        Long moduleId = MapUtils.getLong(map,"id");
        if(moduleId == 0 ){
            return MessageResult.getMessage("E10006");
        }
        String updateUser = MapUtils.getString(map,"updateUser");
        String moduleName = MapUtils.getString(map,"moduleName");
        String modulePath = MapUtils.getString(map,"modulePath");
        Integer position = MapUtils.getInteger(map,"position");
        String moduleType = MapUtils.getString(map,"moduleType");
        String description = MapUtils.getString(map,"description");
        String icons = MapUtils.getString(map,"icons");
        Boolean isVisible = MapUtils.getBoolean(map,"isVisible");
        TWmsModuleEntity moduleEntity = this.findByPrimaryKey(moduleId);
        moduleEntity.setModuleName(moduleName);
        moduleEntity.setModuleType(moduleType);
        moduleEntity.setModulePath(modulePath);
        moduleEntity.setIsVisible(isVisible);
        moduleEntity.setPosition(position);
        moduleEntity.setIcons(StringUtils.isEmpty(icons) ? GlobalConstants.DEFAULT_MENU_ICONS : icons);
        moduleEntity.setDescription(description);
        moduleEntity.setUpdateTime(new java.util.Date().getTime());
        moduleEntity.setUpdateUser(updateUser);
        moduleMapper.updateModule(moduleEntity);
        return MessageResult.getSucMessage();
    }

    @Override
    public List getAllModuleAction() {
        Map paramsMap = new HashMap();
        paramsMap.put("isVisible",new Byte("1"));
        List <TWmsModuleEntity> moduleList = moduleMapper.selectAllModules(paramsMap);
        List <TWmsPermissionEntity> actionList  = permissionService.selectPermissions();
        List <Map> resultList = new ArrayList<>();
        moduleList.forEach(moudle->{
            Map moduleMap = new HashMap();
            moduleMap.put("id",moudle.getId());
            moduleMap.put("name",moudle.getModuleName());
            moduleMap.put("parentId",moudle.getParentId());
            resultList.add(moduleMap);
        });
        actionList.forEach(action->{
            Map actionMap = new HashMap();
            actionMap.put("id","a_"+action.getId());
            String actionName = action.getActionName();
            if(StringUtils.isEmpty(actionName)){
                actionName = action.getDescription();
            }
            actionMap.put("name",actionName);
            actionMap.put("parentId",action.getModuleId());
            resultList.add(actionMap);
        });
        return resultList;
    }

}
