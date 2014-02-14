
package com.example.demo.feb;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

/*
 <entry>
        <id>http://stackoverflow.com/q/21770336</id>
        <re:rank scheme="http://stackoverflow.com">0</re:rank>
        <title type="text">How do I get a seek bar to progress with hex</title>
        <category scheme="http://stackoverflow.com/feeds/tag?tagnames=android&amp;sort=newest/tags" term="java"/><category scheme="http://stackoverflow.com/feeds/tag?tagnames=android&amp;sort=newest/tags" term="android"/>
        <author>
            <name>SketchyTurtle</name>
            <uri>http://stackoverflow.com/users/3273552</uri>
        </author>
        <link rel="alternate" href="http://stackoverflow.com/questions/21770336/how-do-i-get-a-seek-bar-to-progress-with-hex" />
        <published>2014-02-14T03:32:44Z</published>
        <updated>2014-02-14T03:55:03Z</updated>
        <summary type="html">
            xxx
        </summary>
    </entry>
 * @author kc
 */
public class StackOverflowXmlParser {

    public List<Entry> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parse = Xml.newPullParser();
            parse.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parse.setInput(in, null);
            parse.nextTag();
            return readFeed(parse);
        } finally {
            in.close();
        }
    }

    private List<Entry> readFeed(XmlPullParser parse) throws XmlPullParserException, IOException {
        List<Entry> entries = new ArrayList<StackOverflowXmlParser.Entry>();
        parse.require(XmlPullParser.START_TAG, null, "feed");
        while(parse.next() != XmlPullParser.END_TAG) {
            if(parse.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parse.getName();
            Log.v("kc", "parese-->" + name);
            if(name.equals("entry")) {
                entries.add(readEntry(parse));
            } else {
                skip(parse);
            }
        }
        return entries;
    }

    private Entry readEntry(XmlPullParser parse) throws XmlPullParserException, IOException {
        parse.require(XmlPullParser.START_TAG, null, "entry");
        String title = null;
        String summary = null;
        String link = null;
        while( parse.next() != XmlPullParser.END_TAG) {
            if(parse.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parse.getName();
            if(name.equals("title")) {
                title = readTtile(parse);
            } else if(name.equals("summary")) {
                summary = readSummary(parse);
            } else if(name.equals("link")) {
                link = readLink(parse);
            } else {
                skip(parse);
            }
        }
        return new Entry(title, summary, link);
    }

    private void skip(XmlPullParser parse) throws XmlPullParserException, IOException {
        if(parse.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while(depth != 0) {
            switch (parse.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                default:
                    break;
            }
        }
    }

    private String readLink(XmlPullParser parse) throws XmlPullParserException, IOException {
        String link = "";
        parse.require(XmlPullParser.START_TAG, null, "link");
        String tag = parse.getName();
        String relType = parse.getAttributeValue(null, "rel");
        if(tag.equals("link")) {
            if(relType.equals("alternate")) {
                link = parse.getAttributeValue(null, "href");
                parse.nextTag();
            }
        }
        parse.require(XmlPullParser.END_TAG, null, "link");
        return link;
    }

    private String readSummary(XmlPullParser parse) throws XmlPullParserException, IOException {
        parse.require(XmlPullParser.START_TAG, null, "summary");
        String summary = readText(parse);
        parse.require(XmlPullParser.END_TAG, null, "summary");
        return summary;
    }

    private String readTtile(XmlPullParser parse) throws XmlPullParserException, IOException {
        parse.require(XmlPullParser.START_TAG, null, "title");
        String title = readText(parse);
        parse.require(XmlPullParser.END_TAG, null, "title");
        return title;
    }

    private String readText(XmlPullParser parse) throws XmlPullParserException, IOException {
        String result = "";
        if(parse.next() == XmlPullParser.TEXT) {
            result = parse.getText();
            parse.nextTag();
        }
        return result;
    }

    public static class Entry {
        public final String title;
        public final String link;
        public final String summary;

        private Entry(String title, String summary, String link) {
            this.title = title;
            this.summary = summary;
            this.link = link;
        }

        @Override
        public String toString() {
            return "Entry [title=" + title + ", link=" + link;
        }
    }
}
