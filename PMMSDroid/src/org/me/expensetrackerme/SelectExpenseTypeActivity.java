/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.expensetrackerme;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;

import org.me.expensetrackerme.services.CachedData;
import org.xml.sax.SAXException;

import trackit.businessobjects.ExpenseCategory;
import trackit.businessobjects.ExpenseSubType;


/**
 * Demonstrates expandable lists using a custom {@link ExpandableListAdapter}
 * from {@link BaseExpandableListAdapter}.
 */
public class SelectExpenseTypeActivity extends ExpandableListActivity implements OnItemClickListener {

    ExpandableListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up our adapter
        mAdapter = new MyExpandableListAdapter();
        setListAdapter(mAdapter);
        registerForContextMenu(getExpandableListView());
        
    }

    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();

        String title = ((TextView) info.targetView).getText().toString();

        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
            int childPos = ExpandableListView.getPackedPositionChild(info.packedPosition);
            Toast.makeText(this, title + ": Child " + childPos + " clicked in group " + groupPos,
                    Toast.LENGTH_SHORT).show();
            return true;
        } else if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
            Toast.makeText(this, title + ": Group " + groupPos + " clicked", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent data = new Intent();
        data.putExtra("ExpenseTypeId", id);
        setResult(RESULT_OK, data);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Intent data = new Intent();
        data.putExtra("ExpenseTypeId", id);
        ExpenseSubType type = (ExpenseSubType) getExpandableListAdapter().getChild(groupPosition, childPosition);
        data.putExtra("ExpenseTypeName", type.getDescription());
        setResult(RESULT_OK, data);
        finish();
        return true;
    }

    /**
     * A simple adapter which maintains an ArrayList of photo resource Ids.
     * Each photo is displayed as an image. This adapter supports clearing the
     * list of photos and adding a new photo.
     *
     */
    public class MyExpandableListAdapter extends BaseExpandableListAdapter {
        private List<ExpenseCategory> expenseCategories;
        
        public MyExpandableListAdapter() {
        	
        	expenseCategories = CachedData.getInstance().getExpenseCategories();
//            Metadata md;
//            try {
//                md = Metadata.init();
//                expenseCategories = MetadataService.getInstance().getExpenseCategories();
//            } catch (ParserConfigurationException ex) {
//                Logger.getLogger(SelectExpenseTypeActivity.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (SAXException ex) {
//                Logger.getLogger(SelectExpenseTypeActivity.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                Logger.getLogger(SelectExpenseTypeActivity.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }

        public Object getChild(int groupPosition, int childPosition) {
            ExpenseCategory cat = (ExpenseCategory) expenseCategories.get(groupPosition);
            return cat.getSubTypes().get(childPosition);
            //return children[groupPosition][childPosition];
        }

        public long getChildId(int groupPosition, int childPosition) {
            //return childPosition;
            ExpenseCategory cat = (ExpenseCategory) expenseCategories.get(groupPosition);
            ExpenseSubType type = (ExpenseSubType) cat.getSubTypes().get(childPosition);
            return type.getId();
        }

        public int getChildrenCount(int groupPosition) {
            //return children[groupPosition].length;
            ExpenseCategory cat = (ExpenseCategory) expenseCategories.get(groupPosition);
            List<ExpenseSubType> subTypes = cat.getSubTypes();
            if (subTypes == null) {
            	subTypes = CachedData.getInstance().getExpenseTypesForCategory(cat.getId());
            	cat.setSubTypes(subTypes);
            }
            return cat.getSubTypes().size();
        }

        public TextView getGenericView() {
            // Layout parameters for the ExpandableListView
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, 64);

            TextView textView = new TextView(SelectExpenseTypeActivity.this);
            textView.setLayoutParams(lp);
            // Center the text vertically
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            // Set the text starting position
            textView.setPadding(36, 0, 0, 0);
            return textView;
        }

        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                View convertView, ViewGroup parent) {
            TextView textView = getGenericView();
            textView.setText(getChild(groupPosition, childPosition).toString());
            return textView;
        }

        public Object getGroup(int groupPosition) {
            //return groups[groupPosition];
            return expenseCategories.get(groupPosition);
        }

        public int getGroupCount() {
            //return groups.length;
            return expenseCategories.size();
        }

        public long getGroupId(int groupPosition) {
            //return groupPosition;
            ExpenseCategory cat = (ExpenseCategory) expenseCategories.get(groupPosition);
            return cat.getId();
        }

        public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                ViewGroup parent) {
            TextView textView = getGenericView();
            textView.setText(getGroup(groupPosition).toString());
            return textView;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        public boolean hasStableIds() {
            return true;
        }

    }
}
