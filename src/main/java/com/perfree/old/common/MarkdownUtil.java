package com.perfree.old.common;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.apache.commons.lang3.StringUtils;

/**
 * MarkdownUtils
 * @author Perfree
 */
public class MarkdownUtil {

    /**
     * markdown to html
     * @param mdStr mdStr
     * @return String
     */
    public static String mdToHtml(String mdStr) {
        if (StringUtils.isBlank(mdStr)){
            return "";
        }
        MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        Node document = parser.parse(mdStr);
        return  renderer.render(document);
    }
}
