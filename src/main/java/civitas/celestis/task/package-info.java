/**
 * <h2>Task</h2>
 * <p>
 * This package contains a lightweight and versatile task management system.
 * {@link civitas.celestis.task.Task Tasks} are defined as a certain action which
 * can be execute in regular intervals by a {@link civitas.celestis.task.Scheduler scheduler}.
 * </p>
 * <p>
 * The module {@link civitas.celestis.task.Scheduler} can either be used directly
 * (by the class {@link civitas.celestis.task.SyncScheduler} or other default
 * implementations), or by implementing a custom class to handle task scheduling.
 * </p>
 * <p>
 * Tasks contain an action {@link civitas.celestis.task.Task#execute(long)}, and an interval
 * {@link civitas.celestis.task.Task#interval()} which uses milliseconds to denote the
 * amount of time.
 * </p>
 *
 * @see civitas.celestis.task.Task
 * @see civitas.celestis.task.Scheduler
 */
package civitas.celestis.task;