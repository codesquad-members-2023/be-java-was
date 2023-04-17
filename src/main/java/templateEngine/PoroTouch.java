package templateEngine;

import java.io.UnsupportedEncodingException;

import view.ModelAndView;

/**
 * PoroTouch 사용법
 *
 * 1. 문법 : ^P$(속성 Key)P^를 HTML에 입력합니다.
 * 2. Model에서 key에 해당하는 Value를 찾아 HTML을 변경해줍니다.
 */
public class PoroTouch {

    public static String OPENING_TAG = "^P";
    public static String CLOSING_TAG = "P^";

    public static byte[] render(byte[] body, ModelAndView modelAndView) throws UnsupportedEncodingException {
        String bodyString = new String(body);

        StringBuilder sb = new StringBuilder(bodyString);
        //Tag를 포함하고 있으면 Tag의 첫번째 Index를 찾는다.
        if (bodyString.contains(OPENING_TAG)) {
            int startIndex = sb.indexOf(OPENING_TAG);
            int endIndex = sb.indexOf(CLOSING_TAG);
            //Model 속성 이름을 찾는다.
            String modelAttributeKey = sb.substring(startIndex + 3, endIndex).trim();

            Object modelAttribute = modelAndView.getModelAttribute(modelAttributeKey);
            if (modelAttribute != null) {
                sb.replace(startIndex, endIndex + 2, modelAttribute.toString());

                System.out.println(modelAttribute);

                //sb.insert(startIndex, modelAttribute);

                System.out.println(sb);
                return sb.toString().getBytes("UTF-8");
            }
        }


        return body;
    }

}
