package org.example;

public class LoggingListener implements ChangeListener{
    @Override
    public void onChange(ChangeEvent changeEvent) {
        System.out.printf("[CONFIG %s] %s.%s: %s -> %s%n",
                changeEvent.getChangeType(),
                changeEvent.getNamespace(),
                changeEvent.getKeu(),
                changeEvent.getOldValue(),
                changeEvent.getNewValue());
    }
    }