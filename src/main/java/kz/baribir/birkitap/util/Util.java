package kz.baribir.birkitap.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class Util {

    private Util() {}

    public static <S,D> D copyProperties(S s,Class<D> targetClass)
    {
        if(s==null)
            return null;
        D targetObject = null;
        try {
            targetObject = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(s, targetObject);
            return targetObject;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <S, D> List<D> copyPropertiesList(List<S> sourceList, Class<D> targetClass) {
        if(sourceList==null)
            return null;
        List<D> destinationList = new ArrayList<>();
        for (S sourceObject : sourceList) {
            try {
                D targetObject = targetClass.getDeclaredConstructor().newInstance();
                BeanUtils.copyProperties(sourceObject, targetObject);
                destinationList.add(targetObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return destinationList;
    }
}
