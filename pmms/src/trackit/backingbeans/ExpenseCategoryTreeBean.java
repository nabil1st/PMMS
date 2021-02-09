/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package trackit.backingbeans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import java.util.Map.Entry;
import org.richfaces.component.UITree;
import org.richfaces.component.html.HtmlTree;
import org.richfaces.event.NodeExpandedEvent;
import org.richfaces.event.NodeSelectedEvent;
import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;
import trackit.businessobjects.ExpenseCategory;
import trackit.businessobjects.ExpenseSubType;
import trackit.exception.EntityException;
import trackit.util.FacesUtils;

public class ExpenseCategoryTreeBean extends BaseBean {

    private TreeNode rootNode = null;
    protected List<TreeNode> selectedNodeChildren = new ArrayList<TreeNode>();

    private String nodeTitle;

    private String description;

    // keeps track of which nodes are expanded
    protected List<String> expandedNodes = new ArrayList<String>();

    // keeps track of which nodes are selected
    protected List<String> selectedNodes = new ArrayList<String>();

    private void addNodes(TreeNode node) {
        String currentAccountId = FacesUtils.getSessionBean().getCurrentAccountId();

        if (this.serviceLocator == null) {
            this.serviceLocator = FacesUtils.getSessionBean().getServiceLocator();
        }

        List<ExpenseCategory> availableCategories =
                this.serviceLocator.getExpenseCategoryService().
                    getAllExpenseCategoriesForAccount(currentAccountId);

        for (ExpenseCategory cat : availableCategories) {
            TreeNodeImpl catNode = new TreeNodeImpl();
            catNode.setData(cat.getDescription());
            node.addChild(cat.getId(), catNode);
            
            if (cat.getSubTypes() != null && cat.getSubTypes().size() > 0) {
                for (ExpenseSubType subType : cat.getSubTypes()) {
                    TreeNodeImpl typeNode = new TreeNodeImpl();
                    typeNode.setData(subType.getDescription());
                    catNode.addChild(subType.getId(), typeNode);
                }
            } else {
                TreeNode dummyNode = new TreeNodeImpl();
                dummyNode.setData("Add Expense Sub Types");
                catNode.addChild(-1, dummyNode);
            }
        }
    }

    private void loadTree() {        
        rootNode = new TreeNodeImpl();
        addNodes(rootNode);
    }

    public void processSelection(NodeSelectedEvent event) {
        HtmlTree tree = (HtmlTree) event.getComponent();
        nodeTitle = (String) tree.getRowData();
        selectedNodeChildren.clear();
        TreeNode currentNode = tree.getModelTreeNode(tree.getRowKey());
        if (currentNode.getParent() == rootNode){
            selectedNodeChildren.add(currentNode);
        }

        selectedNodes.clear();
        selectedNodes.add(tree.getRowKey().toString());
    }

    public TreeNode getTreeNode() {
        //if (rootNode == null) {
            loadTree();
        //}

        return rootNode;
    }

    public String addCategoryAction() throws EntityException {
        String currentAccountId = FacesUtils.getSessionBean().getCurrentAccountId();

        ExpenseCategory cat = new ExpenseCategory();
        cat.setDescription(description);
        
        if (!currentAccountId.equals("1")) {
            cat.setAccountId(Long.parseLong(currentAccountId));
        }

        this.serviceLocator.getExpenseCategoryService().saveExpenseCategory(cat);
        description = null;
        return NavigationResults.SHOW_MANAGE_EXPENSE_CATEGORIES;
    }

    public Long getKey(TreeNode node) {
        TreeNode parent = node.getParent();
        Iterator children = parent.getChildren();
        while (children.hasNext()) {
            Entry<Long, TreeNode> entry =
                    (Entry<Long, TreeNode>) children.next();
            TreeNode child = entry.getValue();

            if (child.getData().equals(node.getData())) {
                return entry.getKey();
            }
        }

        return null;
    }

    public String addTypeAction() throws EntityException {
        String currentAccountId = FacesUtils.getSessionBean().getCurrentAccountId();
        
        if (selectedNodeChildren.size() > 0) {
            TreeNode selectedNode = selectedNodeChildren.get(0);

            ExpenseSubType subType = new ExpenseSubType();
            subType.setDescription(description);
            subType.setCategoryId(getKey(selectedNode));

            if (!currentAccountId.equals("1")) {
                subType.setAccountId(Long.parseLong(currentAccountId));
            }
            this.serviceLocator.getExpenseCategoryService().saveExpenseSubType(subType);
            
            // find category with name selectedCategory
//            Iterator children = rootNode.getChildren();
//            while (children.hasNext()) {
//                Entry<Long, TreeNode> entry =
//                        (Entry<Long, TreeNode>) children.next();
//                TreeNode child = entry.getValue();
//
//                if (child.getData().equals(selectedCategory)) {
//                    ExpenseSubType subType = new ExpenseSubType();
//                    subType.setDescription(description);
//                    subType.setCategoryId(entry.getKey());
//
//                    if (!currentAccountId.equals("1")) {
//                        subType.setAccountId(Long.parseLong(currentAccountId));
//                    }
//                    this.serviceLocator.getExpenseCategoryService().saveExpenseSubType(subType);
//                    break;
//                }
//            }
            
        } else {
            FacesUtils.addErrorMessage("A Category must be selected");
        }

        description = null;
        return NavigationResults.SHOW_MANAGE_EXPENSE_CATEGORIES;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNodeTitle() {
        return nodeTitle;
    }

    public void setNodeTitle(String nodeTitle) {
        this.nodeTitle = nodeTitle;
    }

    public String doneAction() {
        expandedNodes.clear();
        selectedNodes.clear();
        
        String currentAccountId =
                FacesUtils.getSessionBean().getCurrentAccountId();

        if (FacesUtils.getSessionBean().getFollowOnAction() != null) {
            String followOnAction = FacesUtils.getSessionBean().getFollowOnAction();
            FacesUtils.getSessionBean().setFollowOnAction(null);
            return followOnAction;
        }


        if (currentAccountId.equalsIgnoreCase("1")) {
            return NavigationResults.ADMIN_HOME;
        }

        return NavigationResults.SHOW_EXPENSES;
    }

    /**
     * a visitor pattern mathod called for every node in the tree. Return Boolean.True
     * to indicate that the node should be selected, Boolean.False deselected and null
     * to keep old state.
     * @param tree
     * @return
     */
    public Boolean adviseNodeSelected(UITree tree) {
        if (selectedNodes.contains(tree.getRowKey().toString())) {
            return Boolean.TRUE;
        }
        return null;
    }

    /**
     * a visitor pattern mathod called for every node in the tree. Return Boolean.True
     * to indicate that the node should be expanded, Boolean.False collapsed and null
     * to keep old state.
     * @param tree
     * @return
     */
    public Boolean adviseNodeOpened(UITree tree) {
        if (expandedNodes.contains(tree.getRowKey().toString())) {
            return Boolean.TRUE;
        }
        return null;
    }

    public void changeExpandListener(NodeExpandedEvent event) {
        HtmlTree tree = (HtmlTree) event.getComponent();
        if (expandedNodes.contains(tree.getRowKey().toString())) {
            expandedNodes.remove(tree.getRowKey().toString());
        } else {
            expandedNodes.add(tree.getRowKey().toString());
        }
        //TreeNode currentNode = tree.getModelTreeNode(tree.getRowKey());
    }
}



