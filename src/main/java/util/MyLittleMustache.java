package util;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public  class MyLittleMustache {

    private static final String OBJECT_AND_METHOD = "\\{\\{\\s*([^#^}^/\\s]+)\\s*\\}\\}";
    private static final String SECTION = "\\{\\{\\s*\\#(.*?)\\s*\\}\\}((?s).*?)\\{\\{\\s*\\/(\\1)\\s*\\}\\}";
    private static final String INVERSE_SECTION = "\\{\\{\\s*\\^(.*?)\\s*\\}\\}((?s).*?)\\{\\{\\s*\\/(\\1)\\s*\\}\\}";

    private static final int GROUP_ONE = 1;
    private static final int GROUP_TWO = 2;


    public static String render(Map<String, ?> model, String template) {

        Interpreter interpreter = new Interpreter(model);

        template = replaceSection(interpreter, template);

        template = replaceInverseSection(interpreter, template);

        template = replaceObjectAndMethod(interpreter, template);
        return template;
    }

    private static String replaceSection(Interpreter interpreter, String template) {
        StringBuilder output = new StringBuilder();
        Pattern pattern = Pattern.compile(SECTION);
        Matcher matcher = pattern.matcher(template);

        while (matcher.find()) {
            String objectName = matcher.group(GROUP_ONE);
            Object object = interpreter.interpretObject(objectName);
            if (object == null) {
                matcher.appendReplacement(output, "");
            }

            if (object instanceof Boolean) {
                if (object.equals(true)) {
                    matcher.appendReplacement(output, matcher.group(GROUP_TWO));
                }
                else {
                    matcher.appendReplacement(output, "");
                }
            }
            if (object instanceof Collection<?>) {
                Collection<Object> objects = (Collection<Object>) object;
                String context = matcher.group(GROUP_TWO);
                StringBuilder tempOutput = new StringBuilder();
                Pattern patternIterator = Pattern.compile(OBJECT_AND_METHOD);
                for (Object o : objects) {
                    Matcher matcherOfIterator = patternIterator.matcher(context);
                    StringBuilder matchOutput = new StringBuilder();
                    while (matcherOfIterator.find()) {
                        String methodName = matcherOfIterator.group(GROUP_ONE);
                        String result = interpreter.interpretObjectAndMethodName(o, methodName);
                        matcherOfIterator.appendReplacement(matchOutput, result);
                    }
                    matcherOfIterator.appendTail(matchOutput);
                    tempOutput.append(matchOutput);
                }

                matcher.appendReplacement(output, tempOutput.toString());

            }
        }
        matcher.appendTail(output);
        return output.toString();
    }

    private static String replaceInverseSection(Interpreter interpreter, String template) {
        StringBuilder output = new StringBuilder();
        Pattern pattern = Pattern.compile(INVERSE_SECTION);
        Matcher matcher = pattern.matcher(template);

        while (matcher.find()) {
            String objectName = matcher.group(GROUP_ONE);
            Object object = interpreter.interpretObject(objectName);
            if (object == null) {
                matcher.appendReplacement(output, matcher.group(GROUP_TWO));
            }

            if (object instanceof Boolean) {
                if (object.equals(true)) {
                    matcher.appendReplacement(output, "");
                }
                else {
                    matcher.appendReplacement(output, matcher.group(GROUP_TWO));
                }
            }
        }
        matcher.appendTail(output);
        return output.toString();
    }

    private static String replaceObjectAndMethod(Interpreter interpreter, String template) {
        StringBuilder output = new StringBuilder();
        Pattern pattern = Pattern.compile(OBJECT_AND_METHOD);
        Matcher matcher = pattern.matcher(template);

        while (matcher.find()) {
            String objectAndMethodName = matcher.group(GROUP_ONE);
            String result = interpreter.interpretObjectAndMethodName(objectAndMethodName);

            if (result != null) {
                matcher.appendReplacement(output, result);
            }
        }
        matcher.appendTail(output);
        return output.toString();
    }
}
