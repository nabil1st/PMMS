/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import org.richfaces.event.NodeExpandedEvent;

/**
 *
 * @author ndaoud
 */
public class NodeExpandedListenerImpl implements org.richfaces.event.NodeExpandedListener{

    public void processExpansion(NodeExpandedEvent event){
        event.getSource();
    }

}
