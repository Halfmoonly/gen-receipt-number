package com.lyflexi.genv1.service;

/**
 * @ClassName: ISequenceGeneratorService
 * @Description: 在此添加类描述
 * @Author: ma.honggang
 * @Version: v1.0
 * @Date: 2022/10/8
 */
@FunctionalInterface
public interface ISequenceGeneratorService<T,R> {

    default void  before(T key){}

    default <R> R generator(T key,String rate) {
        before(key);
        R temp = execute(key,rate);
        return after(key,temp);
    }

    <R> R execute(T key,String rate);

    default <R> R after(T key,R value) {
        return value;
    }

    default Long query(T key,String rate){
        return 0L;
    }

}
