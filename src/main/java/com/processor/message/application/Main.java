package com.processor.message.application;

/**
 * Application entry point.
 *
 * @author victor
 */
public class Main {

    public static void main(String[] args) {

        MessageProducer messageProducer = new MessageProducer();
        MessageProcessor messageProcessor = new MessageProcessor();
        SalesReporter salesReporter = new SalesReporter();

        MessageProcessingApplication application = new MessageProcessingApplication(messageProcessor, salesReporter);
        messageProducer.generateAndSendMessageNotifications();
        application.start();

        while(true){
            System.out.println("Press 'Enter' to resume or Ctrl+C to exit: ");
            System.console().readLine();
            application.start();
        }
    }

}
