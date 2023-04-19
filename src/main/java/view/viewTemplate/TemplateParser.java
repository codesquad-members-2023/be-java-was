package view.viewTemplate;

import static view.viewTemplate.TemplateConstans.*;
import static webserver.RequestHandler.logger;

public class TemplateParser {
    public static String formantted(String key, String methodName) {
        return String.format(TEMPLATE_FORMAT, key, methodName);
    }

    public static String parseBefore(String viewString) {
        int startPoint = viewString.indexOf(START_TAG);
        int endPoint = viewString.indexOf(CLOSE_TAG, viewString.indexOf(END_TAG)) + CLOSE_TAG.length();
        return viewString.substring(startPoint, endPoint);
    }

    public static String parseAfter(String viewString) {
        int startPoint = viewString.indexOf(CLOSE_TAG, viewString.indexOf(START_TAG)) + CLOSE_TAG.length();
        int endPoint = viewString.indexOf(END_TAG);
        return viewString.substring(startPoint, endPoint);
    }

    public static String parseUnless(String viewString) {
        if (viewString.contains(UNLESS_END_TAG)) {
            int startPoint = viewString.indexOf(CLOSE_TAG, viewString.indexOf(UNLESS_TAG)) + CLOSE_TAG.length();
            int endPoint = viewString.indexOf(UNLESS_END_TAG);
            return viewString.substring(startPoint, endPoint);
        }
        return null;
    }

    public static String parseKey(String viewString) {
        int startPoint = viewString.indexOf(START_TAG) + START_TAG.length();
        int endPoint = viewString.indexOf(CLOSE_TAG, startPoint);
        logger.debug("parseKey={}", viewString.substring(startPoint, endPoint));
        return viewString.substring(startPoint, endPoint);
    }

    public static String getTag(String viewString, String tagForm) {
        int startPoint = viewString.indexOf(tagForm);
        int endPoint = viewString.indexOf(CLOSE_TAG, startPoint) + CLOSE_TAG.length();
        logger.debug("tag = {}", viewString.substring(startPoint, endPoint));
        return viewString.substring(startPoint, endPoint);
    }
}
