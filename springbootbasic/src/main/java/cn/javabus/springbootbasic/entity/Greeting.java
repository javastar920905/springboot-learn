package cn.javabus.springbootbasic.entity;

import lombok.Data;

/**
 * @author ouzhx on 2019/6/6.
 */
@Data
public class Greeting {
    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }
}
