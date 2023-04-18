package templateEngine;

import java.io.UnsupportedEncodingException;
import java.util.List;

import view.ModelAndView;

/**
 * PoroTouch 사용법
 *
 * 1. 문법 : ^P$(속성 Key)P^를 HTML에 입력합니다. Model에서 key에 해당하는 Value를 찾아 HTML을 변경해줍니다.
 * 2. 반복 기능 : 반복하고 싶은 구간을 ^Start^와 ^End^로 감쌉니다. 구간 내의 ^P$(속성 Key)P^의 개수만큼 HTML을 추가합니다.
 * 성능..?
 */
public class PoroTouch {

    public static String OPENING_TAG = "^P";
    public static String CLOSING_TAG = "P^";
    public static String START_TAG = "^Start^";
    public static String END_TAG = "^End^";

    public static byte[] render(byte[] body, ModelAndView modelAndView) throws UnsupportedEncodingException {
        String bodyString = new String(body);

        StringBuilder sb = new StringBuilder(bodyString);

        //블록 반복 기능
        if (sb.toString().contains(START_TAG)) {
            while (sb.toString().contains(START_TAG)) {
                //반복 블록 구간을 blockBuilder에 저장
                int startOfBlockWithTag = sb.indexOf(START_TAG);
                int startOfBlockWithoutTag = sb.indexOf(START_TAG) + START_TAG.length();

                int endOfBlockWithTag = sb.indexOf(END_TAG) + END_TAG.length();
                int endOfBlockWithoutTag = sb.indexOf(END_TAG);

                if (modelAndView.getModelAttribute("maxCount") == null) {
                    return sb.replace(startOfBlockWithTag, endOfBlockWithTag, "").toString().getBytes("UTF-8");
                }

                StringBuilder blockBuilder = new StringBuilder();
                int maxCount = Integer.parseInt((String)modelAndView.getModelAttribute("maxCount"));
                for (int order = 0; order < maxCount; order++) {
                    blockBuilder.append(sb.substring(startOfBlockWithoutTag, endOfBlockWithoutTag));

                    while (blockBuilder.toString().contains(OPENING_TAG)) {
                        int startIndex = blockBuilder.indexOf(OPENING_TAG);
                        int endIndex = blockBuilder.indexOf(CLOSING_TAG);

                        //Model 속성 이름을 찾는다.
                        String modelAttributeKey =
                                blockBuilder.substring(startIndex + OPENING_TAG.length(), endIndex).trim() + order;

                        Object modelAttribute = modelAndView.getModelAttribute(modelAttributeKey);
                        if (modelAttribute != null) {
                            blockBuilder.replace(startIndex, endIndex + CLOSING_TAG.length(),
                                    modelAttribute.toString());
                        }
                    }
                }

                sb.replace(startOfBlockWithTag, endOfBlockWithTag, blockBuilder.toString());

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
            String modelAttributeKey = sb.substring(startIndex + OPENING_TAG.length(), endIndex).trim();

            Object modelAttribute = modelAndView.getModelAttribute(modelAttributeKey);
            if (modelAttribute != null) {
                sb.replace(startIndex, endIndex + CLOSING_TAG.length(), modelAttribute.toString());
                continue;
            }
            sb.replace(startIndex, endIndex + CLOSING_TAG.length(), "");
        }
    }
}
