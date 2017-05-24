package com.huamengtong.wms.app.controller.login;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huamengtong.wms.constants.GlobalConstants;
import com.huamengtong.wms.core.formwork.db.util.DbShareField;
import com.huamengtong.wms.core.web.BaseController;
import com.huamengtong.wms.core.web.ResponseResult;
import com.huamengtong.wms.entity.main.TWmsCargoOwnerEntity;
import com.huamengtong.wms.entity.main.TWmsLocationEntity;
import com.huamengtong.wms.entity.main.TWmsOrganizationsEntity;
import com.huamengtong.wms.entity.main.TWmsPrintMapEntity;
import com.huamengtong.wms.entity.main.TWmsStorageRoomEntity;
import com.huamengtong.wms.entity.main.TWmsWarehouseEntity;
import com.huamengtong.wms.entity.main.TWmsZoneEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuCategorysEntity;
import com.huamengtong.wms.entity.sku.TWmsSkuEntity;
import com.huamengtong.wms.main.service.ICargoOwnerService;
import com.huamengtong.wms.main.service.ILocationService;
import com.huamengtong.wms.main.service.IModuleService;
import com.huamengtong.wms.main.service.IOrganizationsService;
import com.huamengtong.wms.main.service.IPrintMapService;
import com.huamengtong.wms.main.service.IRoleService;
import com.huamengtong.wms.main.service.IStorageRoomService;
import com.huamengtong.wms.main.service.IWarehouseService;
import com.huamengtong.wms.main.service.IZoneService;
import com.huamengtong.wms.sku.service.ISkuCategorysService;
import com.huamengtong.wms.sku.service.ISkuService;
import com.huamengtong.wms.utils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/index")
public class IndexController extends BaseController {

    @Autowired
    private IWarehouseService warehouseService;

    @Autowired
    private IModuleService moduleService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private ILocationService locationService;

    @Autowired
    private IZoneService zoneService;

    @Autowired
    private ISkuCategorysService skuCategorysService;

    @Autowired
    private ICargoOwnerService cargoOwnerService;

    @Autowired
    IStorageRoomService storageRoomService;

    @Autowired
    IPrintMapService printMapService;

    @Autowired
    IOrganizationsService organizationsService;

    @Autowired
    ISkuService skuService;


    /**
     * 根据用户获取操作权限
     * @return
     */
    @RequestMapping(value="perm",method = RequestMethod.GET)
    public ResponseResult getPermission(){
        Map map = moduleService.getAllModuleActionByUser(this.getSessionCurrentUser());
        map.put("userName",this.getSessionCurrentUser().getUserName());
        List<Long> roleList = (List<Long>) this.getSession().getAttribute(GlobalConstants.ROLE_IDS);
        List <String> roleNameList = roleService.getRoleNameByIds(roleList);
        map.put("roleName",roleNameList.stream().collect(Collectors.joining(",")));
        return getSucResultData(map);
    }

    /***
     * 根据用户获取菜单
     * @return
     */
    @RequestMapping(value="menu",method = RequestMethod.GET)
    public ResponseResult getMenuLists(){
        List list = moduleService.getModulesByUser(this.getSessionCurrentUser());
        return getSucResultData(list);
    }



    /**
     * 根据租户查询所有仓库
     * @return
     */
    @RequestMapping(value = "warehouse/combox",method = RequestMethod.GET)
    public ResponseResult searchWarehouseByTenantIdForCombox(){
        List<TWmsWarehouseEntity> warehouses = warehouseService.searchWarehouseByUser(this.getSessionCurrentUser());
        Map map = new HashMap();
        map.put("list", BeanUtils.convertListToKeyValues(warehouses, TWmsWarehouseEntity.class, "warehouseName", "id"));
        long whId = this.getCurrentWarehouseId();
        if( whId != 0){
            map.put("selected",whId);
        }
        return getSucResultData(map);
    }


    @RequestMapping(value = "warehouse",method = RequestMethod.GET)
    public ResponseResult searchWarehouseByTenantId(){
        List<TWmsWarehouseEntity> warehouses = warehouseService.searchWarehouseByUser(getSessionCurrentUser());
        return getSucResultData(BeanUtils.convertListToKeyValues(warehouses, TWmsWarehouseEntity.class, "warehouseName", "id"));
    }

    @RequestMapping(value = "zone",method = RequestMethod.GET)
    public ResponseResult searchZones(){
        List<TWmsZoneEntity> zoneEntityList = zoneService.searchZoneByWhId(this.getCurrentWarehouseId());
        return getSucResultData(BeanUtils.convertListToKeyValues(zoneEntityList, TWmsZoneEntity.class, "zoneNo", "id"));
    }

    @RequestMapping(value = "location",method = RequestMethod.GET)
    public ResponseResult searchLocation(){
        List<TWmsLocationEntity> warehouses = locationService.searchLocationByTenantId(getSessionCurrentUser().getTenantId());
        return getSucResultData(BeanUtils.convertListToKeyValues(warehouses, TWmsLocationEntity.class, "locationNo", "id"));
    }

    @RequestMapping(value = "location/{zoneId}", method = RequestMethod.GET)
    public ResponseResult searchLocationByZone(@PathVariable Long zoneId) throws Exception {
        Map map = new HashMap();
        map.put("warehouseId", getCurrentWarehouseId());
        map.put("zoneId", zoneId);
        List<TWmsLocationEntity> locations = null;//locationService.searchLocationByZone(map);
        return new ResponseResult(BeanUtils.convertListToKeyValues(locations, TWmsLocationEntity.class, "locationNo", "id"));
    }

    @RequestMapping(value = "skuCategorys/{parentId}",method = RequestMethod.GET)
    public ResponseResult searchParentSkuCategorys(@PathVariable("parentId")Long parentId){
        List<TWmsSkuCategorysEntity> skuCategorysEntityList = skuCategorysService.findAll(getDbShardVO(DbShareField.SKU));
        return getSucResultData(BeanUtils.convertListToKeyValues(skuCategorysEntityList, TWmsSkuCategorysEntity.class, "categoryName", "id"));
    }

    @RequestMapping(value = "categorys",method = RequestMethod.GET)
    public ResponseResult searchSkuCategorys(){
        List<TWmsSkuCategorysEntity> skuCategorysEntityList = skuCategorysService.findByParentId(null,getDbShardVO(DbShareField.SKU));
        return getSucResultData(BeanUtils.convertListToKeyValues(skuCategorysEntityList, TWmsSkuCategorysEntity.class, "categoryName", "id"));
    }

    @RequestMapping(value = "cargoOwner",method = RequestMethod.GET)
    public ResponseResult searchCargoOwners(){
        List<TWmsCargoOwnerEntity> cargoOwnerEntities = cargoOwnerService.findCargoOwner(null);
        return getSucResultData(BeanUtils.convertListToKeyValues(cargoOwnerEntities, TWmsCargoOwnerEntity.class, "shortName", "id"));
    }

    @RequestMapping(value = "cargoOwner/{id}",method = RequestMethod.GET)
    public ResponseResult searchCargoOwners(@PathVariable("id")Long id){
        Map map = new HashMap();
        map.put("id",id);
        List<TWmsCargoOwnerEntity> cargoOwnerEntities = cargoOwnerService.findCargoOwner(map);
        return getSucResultData(BeanUtils.convertListToKeyValues(cargoOwnerEntities, TWmsCargoOwnerEntity.class, "shortName", "id"));
    }

    @RequestMapping(value = "storageRoom/warehouseId",method = RequestMethod.GET)
    public ResponseResult searchStorageRooms(){
        List<TWmsStorageRoomEntity> storageRoomEntities=storageRoomService.findStorageRoomsBywhId(getCurrentWarehouseId());
        return getSucResultData(BeanUtils.convertListToKeyValues(storageRoomEntities,TWmsStorageRoomEntity.class,"roomNo","id"));
    }

    @RequestMapping(value = "printMap",method = RequestMethod.GET)
    public ResponseResult searchPrintMap(){
        List<TWmsPrintMapEntity> printMapEntities= printMapService.findPrintMap();
        return getSucResultData(BeanUtils.convertListToKeyValues(printMapEntities,TWmsPrintMapEntity.class,"name","code"));
    }

    @RequestMapping(value = "organizations",method = RequestMethod.GET)
    public ResponseResult searchOrganizations(){
        List<TWmsOrganizationsEntity> organizationsEntities = organizationsService.queryAll();
        return getSucResultData(BeanUtils.convertListToKeyValues(organizationsEntities,TWmsOrganizationsEntity.class,"name","id"));
    }

    @RequestMapping(value = "sku",method = RequestMethod.GET)
    public ResponseResult serachSkuNameBySkuId(){
          List<TWmsSkuEntity> skuEntities = skuService.querySkuAll(getDbShardVO(DbShareField.SKU));
        return  getSucResultData(BeanUtils.convertListToKeyValues(skuEntities,TWmsSkuEntity.class,"itemName","id"));
    }

    @RequestMapping(value = "storageRoom",method = RequestMethod.GET)
    public ResponseResult searchStorageRoomsByWarehouseId(){
        List<TWmsStorageRoomEntity> storageRoomEntities=storageRoomService.findStorageRoomsBywhId(0L);
        return getSucResultData(BeanUtils.convertListToKeyValues(storageRoomEntities,TWmsStorageRoomEntity.class,"roomNo","id"));
    }





    /**
     * 设置当前仓库
     * @return
     */
    @RequestMapping(value = "warehouse/current/{warehouseId}",method = RequestMethod.PUT)
    public ResponseResult setDefaultWh(@PathVariable Long warehouseId){
        TWmsWarehouseEntity warehouseEntity = warehouseService.findWarehouseById(warehouseId);
        if (warehouseEntity != null) {
            this.setCurrentWarehouseId(warehouseEntity.getId());
            this.setCurrentTenantId(warehouseEntity.getTenantId());
        }
        return getSucMessage();
    }


    /**
     * 深度遍历菜单信息
     * @param menu
     * @param menuKeyList
     * @param permsMap
     * @return
     */
    public List deepParse(JSONObject menu, List<Map> menuKeyList, Map<String, List> permsMap) {
        JSONArray children = (JSONArray) menu.get("children");
        List<Map> currentLevelMenuList = new ArrayList<>();
        if (children != null && children.size() > 0) {
            for (int i = 0; i < children.size(); i++) {
                JSONObject childrenMenu = (JSONObject) children.get(i);
                childrenMenu.put("parentId", childrenMenu.get("parent_id"));

                String type = childrenMenu.getString("type");

                if (StringUtils.isNotBlank(type)) {
                    if ("MENU".equals(type)) {

                        String path = childrenMenu.getString("value");
                        if (path.endsWith("html")) {
                            path = path.substring(0, path.indexOf('.'));
                        }

                        childrenMenu.put("path", path);
                        Map menuMap = copyFromJson(childrenMenu);
                        addMenuKey(childrenMenu, menuKeyList);

                        List subList = deepParse(childrenMenu, menuKeyList, permsMap);
                        menuMap.put("children", subList);
                        currentLevelMenuList.add(menuMap);
                    } else if ("ACTION".equals(type)) {
                        makePermList(childrenMenu, menuKeyList, permsMap);
                    }
                }


            }
        }

        return currentLevelMenuList;
    }

    /**
     * 形成权限列表
     * @param permMap
     * @param menuKeyList
     * @param permsMap
     */
    private void makePermList(JSONObject permMap, List<Map> menuKeyList,Map<String, List> permsMap) {
        String parentId = permMap.getString("parentId");

        Map htmlPath = null;
        for(Map temp:menuKeyList){
            if(temp.get(parentId) != null){
                htmlPath = temp;
                break;
            }
        }
        if (htmlPath != null) {
            final String matchStr = htmlPath.get(parentId).toString();

            List matchList = permsMap.get(matchStr);
            Map permNewMap = new HashMap();
            if (CollectionUtils.isNotEmpty(matchList)) {


                permNewMap.put("path", permMap.getString("value"));
                permNewMap.put("buttonName", permMap.getString("name"));
                permNewMap.put("buttonId", permMap.getString("code"));

                matchList.add(permNewMap);
                permsMap.put(matchStr, matchList);
            } else {

                matchList = new ArrayList();
                permNewMap.put("path", permMap.getString("value"));
                permNewMap.put("buttonName", permMap.getString("name"));
                permNewMap.put("buttonId", permMap.getString("code"));

                matchList.add(permNewMap);
                permsMap.put(matchStr, matchList);
            }
        }

    }

    /**
     * 添加菜单key
     * @param menu
     * @param menuKeyList
     */
    private void addMenuKey(JSONObject menu, List<Map> menuKeyList) {
        Map menuKey = new HashMap<>();
        menuKey.put(menu.get("id"), menu.get("path"));
        menuKeyList.add(menuKey);
    }


    /**
     * 拷贝json
     * @param menuJson
     * @return
     */
    private Map copyFromJson(JSONObject menuJson) {
        Map menuMap = new HashMap();
        menuMap.put("id", menuJson.get("id"));
        menuMap.put("parentId", menuJson.get("parentId"));
        menuMap.put("path", menuJson.get("path"));
        menuMap.put("name", menuJson.get("name"));
        return menuMap;
    }

}
