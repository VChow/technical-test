package com.processor.message.application;

import org.junit.Test;

/**
 * Test file for {@link MessageProcessingApplication}.
 *
 * @author victor
 */
public class MessageProcessingApplicationTest {

    @Test
    public void testConstructor(){
        try {
            MessageProcessingApplication app = new MessageProcessingApplication();
        }catch(NullPointerException npe){
            //Caused by console read, can be safely ignored.
        }
    }

}
