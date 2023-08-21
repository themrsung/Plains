/**
 * <h2>Event</h2>
 * <p>
 * This package contains a lightweight and versatile event management system.
 * The interface {@link civitas.celestis.event.Handleable} is used
 * to define the contract of an event, and the {@link civitas.celestis.event.Listener}
 * and {@link civitas.celestis.event.EventHandler} interfaces mark
 * the presence of an event handler method.
 * </p>
 * <p>
 * The module {@link civitas.celestis.event.EventManager} can either be used
 * directly (by the class {@link civitas.celestis.event.SyncEventManager},
 * or by implementing a custom class to handle event management.
 * </p>
 * <p>
 * Events have two fields by default: a {@link java.util.UUID unique identifier} and a
 * {@link civitas.celestis.event.Handleable cause}. These two fields can be used to identify
 * and determine the cause of the event.
 * </p>
 *
 * @see civitas.celestis.event.Handleable
 * @see civitas.celestis.event.Event
 * @see civitas.celestis.event.Listener
 * @see civitas.celestis.event.EventHandler
 * @see civitas.celestis.event.EventManager
 */
package civitas.celestis.event;