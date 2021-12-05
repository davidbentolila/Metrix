package br.ufpa.linc.MetriX.view.prefuse.action;

import br.ufpa.linc.MetriX.view.prefuse.Visualization;

/**
 * An Action that can be parameterized to process a particular group of items.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public abstract class GroupAction extends Action {

    /** A reference to the group to be processed by this Action */
    protected String m_group;
    
    /**
     * Create a new GroupAction that processes all groups.
     * @see Visualization#ALL_ITEMS
     */
    public GroupAction() {
        this((Visualization)null);
    }

    /**
     * Create a new GroupAction that processes all groups.
     * @param vis the {@link Visualization} to process
     * @see Visualization#ALL_ITEMS
     */
    public GroupAction(Visualization vis) {
        this(vis, Visualization.ALL_ITEMS);
    }
    
    /**
     * Create a new GroupAction that processes all groups.
     * @param vis the {@link Visualization} to process
     * @param duration the duration of this Action
     * @see Visualization#ALL_ITEMS
     */
    public GroupAction(Visualization vis, long duration) {
        this(vis, Visualization.ALL_ITEMS, duration);
    }
    
    /**
     * Create a new GroupAction that processes all groups.
     * @param vis the {@link Visualization} to process
     * @param duration the duration of this Action
     * @param stepTime the time to wait between invocations of this Action
     * @see Visualization#ALL_ITEMS
     */
    public GroupAction(Visualization vis, long duration, long stepTime) {
        this(vis, Visualization.ALL_ITEMS, duration, stepTime);
    }
    
    /**
     * Create a new GroupAction that processes the specified group.
     * @param group the name of the group to process
     */
    public GroupAction(String group) {
        this(null, group);
    }
    
    /**
     * Create a new GroupAction that processes the specified group.
     * @param group the name of the group to process
     * @param duration the duration of this Action
     */
    public GroupAction(String group, long duration) {
        this(null, group, duration);
    }
    
    /**
     * Create a new GroupAction that processes the specified group.
     * @param group the name of the group to process
     * @param duration the duration of this Action
     * @param stepTime the time to wait between invocations of this Action
     */
    public GroupAction(String group, long duration, long stepTime) {
        this(null, group, duration, stepTime);
    }
    
    /**
     * Create a new GroupAction that processes the specified group.
     * @param vis the {@link Visualization} to process
     * @param group the name of the group to process
     */
    public GroupAction(Visualization vis, String group) {
        super(vis);
        m_group = group;
    }
    
    /**
     * Create a new GroupAction that processes the specified group.
     * @param vis the {@link Visualization} to process
     * @param group the name of the group to process
     * @param duration the duration of this Action
     */
    public GroupAction(Visualization vis, String group, long duration) {
        super(vis, duration);
        m_group = group;
    }
    
    /**
     * Create a new GroupAction that processes the specified group.
     * @param vis the {@link Visualization} to process
     * @param group the name of the group to process
     * @param duration the duration of this Action
     * @param stepTime the time to wait between invocations of this Action
     */
    public GroupAction(Visualization vis, String group,
                       long duration, long stepTime)
    {
        super(vis, duration, stepTime);
        m_group = group;
    }

    // ------------------------------------------------------------------------
    
    /**
     * Get the name of the group to be processed by this 
     * @return the name of the group to process
     */
    public String getGroup() {
        return m_group;
    }

    /**
     * Sets the name of the group to be processed by this 
     * @param group the name of the group to process
     */
    public void setGroup(String group) {
        m_group = group;
    }
    
    // ------------------------------------------------------------------------
    
    /**
     * @see Action#run(double)
     */
    public abstract void run(double frac);

} // end of class GroupAction
