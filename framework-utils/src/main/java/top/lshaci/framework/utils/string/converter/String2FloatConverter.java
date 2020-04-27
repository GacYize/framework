package top.lshaci.framework.utils.string.converter;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>Convert the string to float</p><br>
 *
 * <b>1.0.7: </b>使用hutool替换commons lang3<br>
 *
 * @author lshaci
 * @since 0.0.1
 * @version 1.0.7
 */
@Slf4j
public class String2FloatConverter implements StringConverter<Float> {

	@Override
	public Float convert(String source) {
		log.debug("The string is : " + source);

        if (StrUtil.isBlank(source)) {
            return null;
        }
        source = trimSource(source);
        try {
        	return Float.parseFloat(source);
        } catch (Exception e) {
        	log.warn("Parse string to float is error!  --> " + source, e);
        }
        return null;
	}

}
