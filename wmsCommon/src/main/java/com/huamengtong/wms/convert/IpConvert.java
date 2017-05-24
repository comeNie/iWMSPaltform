package com.huamengtong.wms.convert;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.huamengtong.wms.utils.IPUtil;
import org.apache.commons.lang3.StringUtils;

public class IpConvert extends ClassicConverter{
    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        return StringUtils.isEmpty(IPUtil.getLocalIP()) ? "" : IPUtil.getLocalIP();
    }
}
