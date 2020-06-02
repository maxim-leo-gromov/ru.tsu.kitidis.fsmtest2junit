package ru.tsu.kitidis.fsmtest2junit;

import org.eclipse.core.resources.IProject;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class FSMTest2JUnitPluginActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "ru.tsu.kitidis.fsmtest2junit"; //$NON-NLS-1$

	// The shared instance
	private static FSMTest2JUnitPluginActivator plugin = null;
	
	// Eclipse project we work with
	private IProject theProject = null;
	
	// Selected item
	private org.eclipse.jdt.core.IType theSelectedItem = null;
	
	public org.eclipse.jdt.core.IType getTheSelectedItem() {
		return theSelectedItem;
	}

	public void setTheSelectedItem(org.eclipse.jdt.core.IType theItem) {
		this.theSelectedItem = theItem;
	}

	public IProject getTheProject() {
		return theProject;
	}

	public void setTheProject(IProject theProject) {
		if(plugin != null) {
			plugin.theProject = theProject;
		}
	}

	/**
	 * The constructor
	 */
	public FSMTest2JUnitPluginActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static FSMTest2JUnitPluginActivator getDefault() {
		return plugin;
	}

}
