package net.openeye.listviewtester;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private static final int VIEW_TYPE_COUNT = 2;

    private String category;
    private String[] categories;
    private String[] categoryItems;
    private String[] normalItems;

    LayoutInflater inflater;

    public ListViewAdapter(Context context, @Nullable String category) {
        this.category = category;
        inflater = LayoutInflater.from(context);

        reloadData();
    }

    private void reloadData() {
        categories = null;
        categoryItems = null;
        normalItems = null;

        if (category != null) {
            categoryItems = new String[]{"Category Item 1", "Category Item 2"};
        } else {
            categories = new String[]{"Category 1", "Category 2"};
            normalItems = new String[]{"Item 1", "Item 2"};
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 ||
                (category == null && categories != null && position - 1 == categories.length) ||
                (category != null && categoryItems != null && position - 1 == categoryItems.length)) {
            return VIEW_TYPE_HEADER;
        }

        return VIEW_TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    private int numberOfSections() {
        int sections = 0;
        if ((category == null && categories != null && categories.length > 0) ||
                (category != null && categoryItems != null && categoryItems.length > 0)) {
            sections++;
        }

        if (normalItems != null && normalItems.length > 0) {
            sections++;
        }

        return sections;
    }

    @Override
    public int getCount() {
        int count = 0;
        if (category == null && categories != null) {
            count += categories.length;
        } else if (category != null && categoryItems != null) {
            count += categoryItems.length;
        }

        if (normalItems != null) {
            count += normalItems.length;
        }

        return count + numberOfSections();
    }

    @Override
    public Object getItem(int position) {
        // Position 0 is the first section header
        if (position == 0) {
            return null;
        }

        // Check and see if the position is in the OWS section
        // Companies
        if (category == null && categories != null && position <= categories.length) {
            return categories[position - 1];
        }

        // Sites
        if (category != null && categoryItems != null && position <= categoryItems.length) {
            return categoryItems[position - 1];
        }

        // Not the OWS section. Find the offset for the local site section
        if (normalItems != null) {
            int offset = 0;
            if (numberOfSections() == 2) {
                if (category == null && categories != null) {
                    offset = categories.length;
                } else if (category != null && categoryItems != null) {
                    offset = categoryItems.length;
                }

                // This is the second section header
                if (position - 1 == offset) {
                    return null;
                }
            }

            int index = position - offset - numberOfSections();
            return normalItems[index];
        }

        return null;
    }

    @Override
    public boolean isEnabled(int position) {
        return getItemViewType(position) == VIEW_TYPE_ITEM;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        int viewType = getItemViewType(position);

        if (convertView == null) {
            viewHolder = new ViewHolder();

            if (viewType == VIEW_TYPE_HEADER) {
                convertView = inflater.inflate(R.layout.list_header_layout, parent, false);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.list_header_textview);
            } else {
                convertView = inflater.inflate(R.layout.list_item_layout, parent, false);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.list_item_textview);
            }

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (viewType == VIEW_TYPE_ITEM) {
            String item = (String) getItem(position);
            viewHolder.textView.setText(item);
        }

        return convertView;
    }

    private class ViewHolder {
        TextView textView;
    }
}
