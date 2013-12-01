package org.mpto.montebook;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.mpto.montebook.model.MonteStaff;

import com.activeandroid.Model;
import com.activeandroid.extras.ModelLoader;
import com.activeandroid.query.Select;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class StaffDirectoryFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<List<MonteStaff>> {
	private View fragment;
	private MonteStaffAdapter staffListAdapter;
	public enum RowType {
        LIST_ITEM, HEADER_ITEM
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		fragment = inflater.inflate(R.layout.staff_directory, container, false);
		staffListAdapter = new MonteStaffAdapter(this.getActivity().getBaseContext(), R.layout.staff_row);
		setListAdapter(staffListAdapter);
		getLoaderManager().initLoader(0, null, this);
		
		return fragment;
	}

	public Loader<List<MonteStaff>> onCreateLoader(int id, Bundle args) {
		return new ModelLoader<MonteStaff>(this.getActivity(),
				MonteStaff.class, 
				new Select()
			    .from(MonteStaff.class)
			    .orderBy("title, lastName"), true);
	}

	public void onLoadFinished(Loader<List<MonteStaff>> loader,
			List<MonteStaff> data) {
		staffListAdapter.clear();
		
		String lastHeader = "";
		for(Iterator<MonteStaff> i = data.iterator(); i.hasNext(); ) {
		    MonteStaff staff = i.next();
		    if (!staff.title.equals(lastHeader)) {
		    	// TODO: Add a header object equal to the title
			    String[] tmp = staff.title.split("_");
			    staffListAdapter.add(new HeaderItem(tmp[1]));
			    lastHeader = staff.title;
		    }
		    // Add the list item to the adapter
		    staffListAdapter.add(new ListItem(staff));
		}		
		staffListAdapter.notifyDataSetChanged();

	}

	public void onLoaderReset(Loader<List<MonteStaff>> loader) {
		staffListAdapter.clear();
		staffListAdapter.notifyDataSetChanged();
	}

	public interface Item {
	    public int getViewType();
	    public View getView(LayoutInflater inflater, View convertView);
	}
	
	public class HeaderItem implements Item {
	    private final String         name;

	    public HeaderItem(String name) {
	        this.name = name;
	    }

	    @Override
	    public int getViewType() {
	        return RowType.HEADER_ITEM.ordinal();
	    }

	    @Override
	    public View getView(LayoutInflater inflater, View convertView) {
	        View view;
	        if (convertView == null) {
	            view = (View) inflater.inflate(R.layout.list_header, null);
	            // Do some initialization
	        } else {
	            view = convertView;
	        }

	        TextView text = (TextView) view.findViewById(R.id.separator);
	        text.setText(name);

	        return view;
	    }

	}
	
	   public class ListItem implements Item {
		   MonteStaff staff;

		    public ListItem(MonteStaff staff) {
		        this.staff = staff;
		    }

		    @Override
		    public int getViewType() {
		        return RowType.LIST_ITEM.ordinal();
		    }

		    @Override
		    public View getView(LayoutInflater inflater, View convertView) {
				View view = convertView;
				if (view == null) {
					view = inflater.inflate(R.layout.staff_row, null);
				}
				// Populate the data into the template view using the data object
				TextView tvName = (TextView) view.findViewById(R.id.staff_tvName);
				TextView tvEmail = (TextView) view.findViewById(R.id.staff_tvEmail);
				TextView tvPhone = (TextView) view
						.findViewById(R.id.staff_tvExtension);
				tvName.setText(staff.salutation + " " + staff.firstName + " "
						+ staff.lastName);
				tvEmail.setText(staff.email);
				tvPhone.setText(staff.extension);
				return view;
		    }

		}
	
	private class MonteStaffAdapter extends ArrayAdapter<Item> {
	    private LayoutInflater mInflater;

		public MonteStaffAdapter(Context context, int resource) {
			super(context, resource);
	        mInflater = LayoutInflater.from(context);
		}

	    @Override
	    public int getViewTypeCount() {
	        return RowType.values().length;

	    }

	    @Override
	    public int getItemViewType(int position) {
	        return getItem(position).getViewType();
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        return getItem(position).getView(mInflater, convertView);
	    }

	}

}
