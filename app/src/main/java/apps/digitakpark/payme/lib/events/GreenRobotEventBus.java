package apps.digitakpark.payme.lib.events;

public class GreenRobotEventBus implements EventBus {

    org.greenrobot.eventbus.EventBus eventBus;

    public GreenRobotEventBus() {
        this.eventBus = org.greenrobot.eventbus.EventBus.getDefault();
    }

    @Override
    public void register(Object subscriber) {
        eventBus.register(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        eventBus.unregister(subscriber);
    }

    @Override
    public void post(Object event) {
        eventBus.post(event);
    }

    public static GreenRobotEventBus getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private final static GreenRobotEventBus INSTANCE = new GreenRobotEventBus();
    }

}
