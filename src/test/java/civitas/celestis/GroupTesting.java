package civitas.celestis;

import civitas.celestis.event.Event;
import civitas.celestis.event.Events;
import civitas.celestis.event.notification.NotificationEvent;

public class GroupTesting {
    public static void main(String[] args) {
        final Event e1 = new Event();
        final Event e2 = new Event(e1);
        final Event e3 = new Event(e2);
        final NotificationEvent e4 = new NotificationEvent("Hello World!", e3);

//        Events.printEventCauseTrace(System.out, e4);
        System.out.println(e4);
    }
}
