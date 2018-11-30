package com.belonk.test.jackson;

import com.alibaba.fastjson.JSON;
import com.belonk.common.json.JacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sun on 2018/11/1.
 *
 * @author sunfuchang03@126.com
 * @version 1.0
 * @since 1.0
 */
public class JacksonUtilTest {
    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Static fields/constants/initializer
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Instance fields
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */



    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Constructors
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */



    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Public Methods
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    @Test
    public void testToJson() {
        Student student = new Student();
        student.setClassName("class1");
        student.setName("cheng");
        student.setAge(3);
        String json = JacksonUtil.toJson(student);
        System.out.println(json);
    }

    @Test
    public void testFromJson() {
        String json = "{\"name\":\"cheng\",\"age\":3,\"className\":\"class1\"}";
        Student student = JacksonUtil.fromJson(json, Student.class);
        Assert.assertNotNull(student);
        System.out.println(student.getClassName());
        System.out.println(student.getAge());
        System.out.println(student.getName());
    }

    @Test
    public void testToJson1() {
        MyPager myPager = new MyPager();
        myPager.setSEcho(2);
        // 字段命名不规范，jackson生成的有问题
        System.out.println(JacksonUtil.toJson(myPager));
        // {"aaData":[],"name":null,"status":null,"subjectId":null,"secho":2,"itotalRecords":0,"itotalDisplayRecords":0}
        System.out.println(JacksonUtil.toJsonWithNull(myPager));
        // {"aaData":[],"iTotalDisplayRecords":0,"iTotalRecords":0,"sEcho":2}
        System.out.println(JSON.toJSONString(myPager));
        Gson gson = new Gson();
        // {"iTotalRecords":0,"iTotalDisplayRecords":0,"sEcho":2,"aaData":[]}
        System.out.println(gson.toJson(myPager));
    }

    @Test
    public void testFromJson1() {
        String json = "{\"iDisplayStart\":0,\"iDisplayLength\":10,\"sEcho\":1,\"iColumns\":7,\"sColumns\":\"\",\"iSortingCols\":0,\"iSortCol_0\":null,\"sSortDir_0\":null,\"orderCol\":null,\"name\":\"\",\"status\":null,\"subjectId\":\"11227\"}";
        MyPager myPager = JacksonUtil.fromJson(json, MyPager.class);
        Assert.assertNotNull(myPager);
        System.out.println(myPager.getStatus());
        System.out.println(myPager.getSubjectId());
        System.out.println(myPager.getName());
        System.out.println(myPager.getSEcho());
    }

    @Test
    public void testToJson3() throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        map.put("e", null);
        map.put("B", "");
        map.put("a", 12);
        Student student = new Student();
        student.setName("zhansan");
        // student.setClassName("class1");
        student.setClassName("");
        // student.setAge(100);
        student.setAge(null);
        map.put("student", student);
        map.put("c", 12);
        System.out.println(JacksonUtil.toJson(map));
        System.out.println(JacksonUtil.toJsonWithNull(map));

        System.out.println(JacksonUtil.toJson(student));
        System.out.println(JacksonUtil.toJsonWithNull(student));

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(map);
        System.out.println(json);
        json = om.writeValueAsString(student);
        System.out.println(json);
    }

    @Test
    public void testFromJson3() {
        String s = "{\"className\":\"classs1\", \"name1\":\"zhangsan\"}";
        // 有没有匹配的属性，转换出来的对象为null
        Student student = JacksonUtil.fromJson(s, Student.class);
        Assert.assertNull(student);
        // 忽略不匹配的字段
        student = JacksonUtil.fromJsonIgnoreUnknown(s, Student.class);
        Assert.assertNotNull(student);
        System.out.println(student.getClassName());
        System.out.println(student.getAge());
        System.out.println(student.getName());
    }

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Protected Methods
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
    
    
    
    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Property accessors
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
     
     
     
    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Inner classes
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */


}