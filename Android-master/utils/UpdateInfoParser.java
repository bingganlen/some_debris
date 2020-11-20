package com.megain.nfctemp.utils.downloadservice.services;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;

/**
 * 解析从服务器获取到的xml文件的内容
 */
public class UpdateInfoParser {
    public static UpdateInfo getUpdateInfo(InputStream is) throws Exception {
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "utf-8");
        int type = parser.getEventType();
        UpdateInfo info = new UpdateInfo();
        while (type != XmlPullParser.END_DOCUMENT) {
            switch (type) {
                case XmlPullParser.START_TAG:
                    switch (parser.getName()) {
                        case "version":
                            info.setVersion(parser.nextText());
                            break;
                        case "url":
                            info.setUrl(parser.nextText());
                            break;
                        case "description":
                            info.setDescription(parser.nextText());
                            break;
                    }
                    break;
            }
            type = parser.next();
        }
        return info;
    }
}

