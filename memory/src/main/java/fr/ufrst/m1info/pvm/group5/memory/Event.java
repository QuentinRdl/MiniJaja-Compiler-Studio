package fr.ufrst.m1info.pvm.group5.memory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

/**
 * Simple implementation of an event class of the publish-subscribe pattern
 * An event is a publisher class. Other classes can subscribe to an event, and will be notified when the event is triggered.
 * The class the event is part of will be in charge of triggering the event
 * @param <T>
 */
public class Event<T> {
    /**
     * Consumers subscribed to the event
     */
    private final ConcurrentLinkedQueue<Consumer<T>> _subscribers;

    public Event() {
        _subscribers = new ConcurrentLinkedQueue<>();
    }

    /**
     * Subscribe to the event. The function will be called when the event is triggered
     * @param subscriber function to add to the event
     */
    public void subscribe(Consumer<T> subscriber) {
        _subscribers.add(subscriber);
    }

    /**
     * Remove a subscribed function from the event
     * @param subscriber function to remove
     */
    public void unsubscribe(Consumer<T> subscriber) {
        _subscribers.remove(subscriber);
    }

    /**
     * Trigger the event with the event data
     * /!\ This function should not be called by subscribers
     * @param eventData data of the event
     */
    public void Trigger(T eventData){
        for(Consumer<T> c : _subscribers){
            c.accept(eventData);
        }
    }

    /**
     * Asynchronously Trigger the event with the event data
     * /!\ This function should not be called by subscribers
     * @param eventData data of the event
     */
    public CompletableFuture<Void> TriggerAsync(T eventData){
        return CompletableFuture.supplyAsync(()-> {
            for (Consumer<T> c : _subscribers) {
                c.accept(eventData);
            }
            return null;
        });
    }
}
