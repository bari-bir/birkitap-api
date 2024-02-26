package kz.baribir.birkitap.util;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtil {

    private ReflectionUtil() {}

    public static List<String> getAllUrlRequestMapping(ConfigurableApplicationContext run) {

        List<String> result = new ArrayList<>();

        String[] beanNamesForAnnotation = run.getBeanNamesForAnnotation(RestController.class);
        for (String str : beanNamesForAnnotation) {
            Object bean = run.getBean(str);
            Class<?> forName = bean.getClass();
            //System.out.println(forName.getName());
            RequestMapping declaredAnnotation = AnnotationUtils.findAnnotation(forName, RequestMapping.class);

            String url_path = "";
            if (declaredAnnotation != null) {
                String[] value = (declaredAnnotation.value());
                //获取类的url路径
                url_path = value[0];
                for (Method method : forName.getDeclaredMethods()) {
                    RequestMapping annotation2 = AnnotationUtils.findAnnotation(method, RequestMapping.class);
                    if (annotation2 != null && annotation2.value().length >= 1) {
                        url_path += annotation2.value()[0];
                        result.add(url_path);
                    }
                    url_path = value[0];
                }
            }
        }

        return result;
    }

    public static List<String> getAllUrlGetMapping(ConfigurableApplicationContext run) {
        List<String> result = new ArrayList<>();
        String[] beanNamesForAnnotation = run.getBeanNamesForAnnotation(RestController.class);
        for (String str : beanNamesForAnnotation) {
            Object bean = run.getBean(str);
            Class<?> forName = bean.getClass();
            //System.out.println(forName.getName());
            RequestMapping declaredAnnotation = AnnotationUtils.findAnnotation(forName, RequestMapping.class);
            String url_path = "";
            if (declaredAnnotation != null) {
                String[] value = (declaredAnnotation.value());
                //获取类的url路径
                url_path = value[0];
                for (Method method : forName.getDeclaredMethods()) {
                    GetMapping annotation2 = AnnotationUtils.findAnnotation(method, GetMapping.class);
                    if (annotation2 != null && annotation2.value().length >= 1) {
                        url_path += annotation2.value()[0];
                        result.add(url_path);
                    }
                    url_path = value[0];
                }
            }
        }

        return result;
    }

    public static List<String> getAllUrlPostMapping(ConfigurableApplicationContext run) {
        List<String> result = new ArrayList<>();
        String[] beanNamesForAnnotation = run.getBeanNamesForAnnotation(RestController.class);
        for (String str : beanNamesForAnnotation) {
            Object bean = run.getBean(str);
            Class<?> forName = bean.getClass();
            //System.out.println(forName.getName());
            RequestMapping declaredAnnotation = AnnotationUtils.findAnnotation(forName, RequestMapping.class);
            String url_path = "";
            if (declaredAnnotation != null) {
                String[] value = (declaredAnnotation.value());
                //获取类的url路径
                url_path = value[0];
                for (Method method : forName.getDeclaredMethods()) {
                    PostMapping annotation2 = AnnotationUtils.findAnnotation(method, PostMapping.class);
                    if (annotation2 != null && annotation2.value().length >= 1) {
                        url_path += annotation2.value()[0];
                        result.add(url_path);
                    }
                    url_path = value[0];
                }
            }
        }

        return result;
    }

}
