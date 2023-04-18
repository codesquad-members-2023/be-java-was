package templateEngine;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import view.ModelAndView;

/**
 * PoroTouch 사용법
 * <p>
 * 1. 문법 : ^P$(속성 Key)P^를 HTML에 입력합니다. Model에서 key에 해당하는 Value를 찾아 HTML을 변경해줍니다. 2. 반복 기능 : 반복하고 싶은
 * 구간을 ^Start^와 ^End^로 감쌉니다. 구간 내의 ^P$(속성 Key)P^의 개수만큼 HTML을 추가합니다. 성능..?
 */
public class PoroTouch {

    public static String OPENING_TAG = "^P";
    public static String CLOSING_TAG = "P^";
    public static String BLOCK_OPENING_TAG = "^#";
    public static String BLOCK_OPENING_END_TAG = "#^";
    public static String BLOCK_CLOSING_TAG = "^/";
    public static String BLOCK_CLOSING_END_TAG = "/^";

    public static byte[] render(byte[] body, ModelAndView modelAndView)
        throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder(new String(body));

        //블록 반복 기능
        if (sb.toString().contains(BLOCK_OPENING_TAG)) {
            while (sb.toString().contains(BLOCK_OPENING_TAG)) {
                //블록 반복 구간의 객체 Key를 읽어온다.
                int startBlockOpeningIdx = sb.indexOf(BLOCK_OPENING_TAG);
                int startBlockClosingIdx = sb.indexOf(BLOCK_OPENING_END_TAG);
                String objectAttributeKey = sb.substring(
                    startBlockOpeningIdx + BLOCK_OPENING_TAG.length(), startBlockClosingIdx).trim();

                //반복 블록 구간을 blockBuilder에 저장
                int startOfBlockWithoutTag = startBlockClosingIdx + BLOCK_OPENING_END_TAG.length();

                int endOfBlockWithTag =
                    sb.indexOf(BLOCK_CLOSING_END_TAG) + BLOCK_CLOSING_END_TAG.length();
                int endOfBlockWithoutTag = sb.indexOf(BLOCK_CLOSING_TAG);

                StringBuilder blockBuilder = new StringBuilder();

                Object object = modelAndView.getModelAttribute(objectAttributeKey);
                if (object instanceof List) {
                    for (Object item : (List) object) {
                        //TODO : List<List<>>인 경우 오브젝트에 대한 로직을 재귀적으로 수행하도록 구현하면 좋을듯?
                        blockBuilder.append(
                            sb.substring(startOfBlockWithoutTag, endOfBlockWithoutTag));
                        renderBlock(blockBuilder, item);
                    }
                    sb.replace(startBlockOpeningIdx, endOfBlockWithTag, blockBuilder.toString());
                } else {
                    blockBuilder.append(
                        sb.substring(startOfBlockWithoutTag, endOfBlockWithoutTag));

                    renderTags(modelAndView, blockBuilder);
                    sb.replace(startBlockOpeningIdx, endOfBlockWithTag, blockBuilder.toString());
                }
            }

            return sb.toString().getBytes("UTF-8");
        }

        if (sb.toString().contains(OPENING_TAG)) {
            //기본 태그 처리
            renderTags(modelAndView, sb);
            return sb.toString().getBytes("UTF-8");
        }

        return body;
    }

    private static void renderTags(ModelAndView modelAndView, StringBuilder sb) {
        while (sb.toString().contains(OPENING_TAG)) {
            int startIndex = sb.indexOf(OPENING_TAG);
            int endIndex = sb.indexOf(CLOSING_TAG);
            //Model 속성 이름을 찾는다.
            String modelAttributeKey = sb.substring(startIndex + OPENING_TAG.length(), endIndex)
                .trim();

            Object modelAttribute = modelAndView.getModelAttribute(modelAttributeKey);

            //String인 경우 그대로 출력

            if (modelAttribute != null) {
                if (modelAttribute instanceof String) {
                    sb.replace(startIndex, endIndex + CLOSING_TAG.length(),
                        modelAttribute.toString());
                    continue;
                }
            }
            sb.replace(startIndex, endIndex + CLOSING_TAG.length(), "");
        }

    }

    private static void renderBlock(StringBuilder sb, Object item) {
        while (sb.toString().contains(OPENING_TAG)) {
            int startIndex = sb.indexOf(OPENING_TAG);
            int endIndex = sb.indexOf(CLOSING_TAG);
            //Model 속성 이름을 찾는다.
            String fieldKey = sb.substring(startIndex + OPENING_TAG.length(), endIndex).trim();

            if (item != null) {
                Method getter = Arrays.stream(
                        item.getClass().getDeclaredMethods())
                    .filter(method -> method.getName().startsWith("get") && method.getName()
                        .toLowerCase()
                        .endsWith(fieldKey.toLowerCase()))
                    .findFirst().orElseThrow(() -> {
                        throw new IllegalArgumentException("존재하지 않는 모델입니다.");
                    });
                try {
                    Object object = getter.invoke(item);
                    sb.replace(startIndex, endIndex + CLOSING_TAG.length(),
                        String.valueOf(object));
                    continue;
                } catch (IllegalAccessException |
                         InvocationTargetException e) {
                    throw new IllegalArgumentException();
                }
            }
            sb.replace(startIndex, endIndex + CLOSING_TAG.length(), "");
        }

    }
}

