package com.platform.machinelearningplatform.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @BelongsProject: security01
 * @BelongsPackage: com.zem.security01.utils
 * @Author: EnMing Zhang
 * @CreateTime: 2022-11-07  09:14
 * @Description: TODO
 * @Version: 1.0
 */
public class WebUtils {
    public static String renderString(HttpServletResponse httpServletResponse, String s){
          try {
              httpServletResponse.setStatus(200);
              httpServletResponse.setContentType("application/json");
              httpServletResponse.setCharacterEncoding("UTF-8");
              httpServletResponse.getWriter().print(s);
          }catch (IOException e) {
              e.printStackTrace();
          }
          return null;
    }
}
