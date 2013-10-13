/*
 * Copyright 2013 David Laurell
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package net.daverix.logcatviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LogAdapter extends BaseAdapter {
    private final Context mContext;
    private List<LogItem> mItems;
    private LayoutInflater mInflater;

    public LogAdapter(Context context, List<LogItem> items) {
        super();

        if(context == null)
            throw new IllegalArgumentException("context is null");

        if(items == null)
            throw new IllegalArgumentException("items is null");

        mContext = context;
        mItems = items;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public void setItems(List<LogItem> items) {
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public LogItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    private LayoutInflater getInflater() {
        if(mInflater == null)
            mInflater = LayoutInflater.from(mContext);

        return mInflater;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = getInflater().inflate(R.layout.log_list_item, null);
            if(convertView == null)
                throw new IllegalStateException("LayoutInflater could not inflate view!");

            holder = new ViewHolder();
            holder.message = (TextView) convertView.findViewById(R.id.textMessage);
            holder.date = (TextView) convertView.findViewById(R.id.textDate);
            holder.tag = (TextView) convertView.findViewById(R.id.textTag);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        LogItem item = getItem(position);
        holder.message.setText(item.getMessage());
        holder.tag.setText(getPriorityString(item.getPriority()) + "/" + item.getTag());
        holder.date.setText(getDateString(item.getDate()));
        holder.message.setTextColor(getColor(item.getPriority()));

        return convertView;
    }

    private String getPriorityString(Priority priority) {
        switch (priority) {
            case ERROR:     return "E";
            case DEBUG:     return "D";
            case FATAL:     return "F";
            case INFO:      return "I";
            case SILENT:    return "S";
            case VERBOSE:   return "V";
            case WARNING:   return "W";
            default:        return "UNKNOWN";
        }
    }

    private String getDateString(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return String.format("%02d-%02d %02d:%02d:%02d.%03d",
                cal.get(Calendar.MONTH)+1,
                cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                cal.get(Calendar.SECOND),
                cal.get(Calendar.MILLISECOND));
    }

    private int getColor(Priority priority) {
        switch (priority) {
            case FATAL:
            case ERROR:
                return 0xFFCC0000; //red
            case WARNING:
                return 0xFFFF8800; //orange
            case INFO:
                return 0xFF669900; //green
            case DEBUG:
                return 0xFF0099CC; //blue
            case VERBOSE:
            default:
                return 0xFF333333; //gray
        }
    }

    private static class ViewHolder {
        public TextView date;
        public TextView tag;
        public TextView message;
    }
}
