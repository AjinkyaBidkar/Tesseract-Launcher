package com.ajinkya.androidlauncherapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Ajinkya on 12/01/22.
 */

public class AppsDrawerAdapter extends RecyclerView.Adapter<AppsDrawerAdapter.ViewHolder> implements Filterable {

    private static Context context;
    private static List<AppInfo> appsList = new ArrayList<>();
    private static List<AppInfo> sortList = new ArrayList<>();


    public AppsDrawerAdapter(Context c) {
        context = c;
        appsList.clear();
        setUpApps();
    }


    public static void setUpApps() {

        PackageManager pManager = context.getPackageManager();
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        appsList = new ArrayList<AppInfo>();
        sortList = new ArrayList<>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> allApps = pManager.queryIntentActivities(i, 0);
        for (ResolveInfo ri : allApps) {
            AppInfo app = new AppInfo();
            app.label = (String) ri.loadLabel(pManager);
            app.packageName = (String) ri.activityInfo.packageName;

            try {
                pInfo = context.getPackageManager().getPackageInfo(app.packageName, 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            app.versionName = pInfo.versionName;
            app.versionCode = String.valueOf(pInfo.versionCode);
            app.icon = ri.activityInfo.loadIcon(pManager);
            appsList.add(app);
            sortList.add(app);
        }
        // sort the list
        Collections.sort(appsList, ALPHA_COMPARATOR);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //This is what adds the code we've written in here to our target view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_view_list, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(appsList.get(position).label.toString());
        holder.textView2.setText(appsList.get(position).packageName.toString());
        holder.tv_app_version.setText(" (V" + appsList.get(position).versionName.toString() + ")");
        holder.tv_app_vcode.setText("Version Code: " + appsList.get(position).versionCode.toString());
        holder.img.setImageDrawable(appsList.get(position).icon);
    }

    @Override
    public int getItemCount() {
        return appsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView, textView2, tv_app_version, tv_app_vcode;
        public ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_app_name);
            textView2 = itemView.findViewById(R.id.tv_pkg_name);
            tv_app_version = itemView.findViewById(R.id.tv_app_version);
            tv_app_vcode = itemView.findViewById(R.id.tv_app_vcode);
            img = itemView.findViewById(R.id.app_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = context.getPackageManager().getLaunchIntentForPackage(textView2.getText().toString());

                    if (intent != null) {
                        context.startActivity(intent);
                    }
                }
            });
        }


    }

    public static final Comparator<AppInfo> ALPHA_COMPARATOR = new Comparator<AppInfo>() {
        private final Collator sCollator = Collator.getInstance();

        @Override
        public int compare(AppInfo object1, AppInfo object2) {
            return sCollator.compare(object1.getLabel(), object2.getLabel());
        }
    };


    @Override
    public Filter getFilter() {
        return Searched_Filter;
    }

    private Filter Searched_Filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<AppInfo> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                // filteredList.addAll(appsList);
                filteredList.addAll(sortList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                Log.i("SEARCH:::TEXT::: ", filterPattern);
                Log.i("SEARCH:::Applist::: ", appsList.toString());
                for (AppInfo item : sortList) {
                    final String app_name = item.getLabel().toLowerCase();
                    final String pkg_name = item.getPackageName().toLowerCase();
                    //value.startsWith(constraint.toString()

                    if (app_name.startsWith(filterPattern) || pkg_name.startsWith(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            results.values = filteredList;
            //notifyDataSetChanged();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            appsList.clear();
            Log.i("ARRAY::FILTERED::: ", String.valueOf(filterResults.values));
            appsList.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };

}


