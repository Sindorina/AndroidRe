package cn.signp.xlogsimple.formatter.message.object;

import android.os.Bundle;

import cn.signp.xlogsimple.internal.util.ObjectToStringUtil;

/**
 * Format an Bundle object to a string.
 *
 * @since 1.4.0
 */
public class BundleFormatter implements ObjectFormatter<Bundle> {

    /**
     * Format an Bundle object to a string.
     *
     * @param data the Bundle object to format
     * @return the formatted string
     */
    @Override
    public String format(Bundle data) {
        return ObjectToStringUtil.bundleToString(data);
    }
}