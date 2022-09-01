package site.easystartup.web.notification.event;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Scope("singleton")
public class NotificationEventManager {
    private List<NotificationListener> listeners = new ArrayList<>();

    public void registerListener(NotificationListener listener) {
        listeners.add(listener);
    }

    public void removeListener(NotificationListener listener) {
        listeners.remove(listener);
    }

    public void notifyListeners(Class<? extends NotificationEvent> event) {
        listeners.forEach(listener -> Arrays.stream(listener.getClass().getMethods()).
                filter(m -> m.isAnnotationPresent(EventHandler.class)).forEach(method -> {
                    if (method.getParameterTypes()[0].equals(event.getClass())) {
                        try {
                            method.invoke(listener, event);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }));
    }
}
