package com.aarushi.crime_mappingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.aarushi.crime_mappingapp.Models.ReportedCrimes;
import com.aarushi.crime_mappingapp.R;

import java.util.List;

/**
 * Created by arushiarora on 3/29/2018.
 */

public class CrimeReportingRecyclerViewAdapter extends RecyclerView.Adapter
        <CrimeReportingRecyclerViewAdapter.CrimeViewHolder>{



    List<ReportedCrimes> crimes;
    Context context;

    public CrimeReportingRecyclerViewAdapter(List<ReportedCrimes> crimes, Context context) {
        this.crimes = crimes;
        this.context = context;
    }

    @Override
    public CrimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.crime_user
                ,parent,false);
        CrimeViewHolder crimeViewHolder=new CrimeViewHolder(itemView);
        return crimeViewHolder;
    }


    @Override
    public void onBindViewHolder(CrimeViewHolder holder, int position) {

        ReportedCrimes thisCrime=crimes.get(position);
        holder.tv_crimeType.setText(thisCrime.getCrime_type());
        holder.tv_crimeStatus.setText(thisCrime.getStatus());
        holder.tv_crimeDate.setText(thisCrime.getDate_crime());
        holder.tv_crimeTime.setText(thisCrime.getTime_crime());
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return crimes.size();
    }

    static class CrimeViewHolder extends RecyclerView.ViewHolder{
        TextView tv_crimeType,tv_crimeStatus,tv_crimeDate,tv_crimeTime;

        public CrimeViewHolder(View itemView) {
            super(itemView);
            tv_crimeType=(TextView)itemView.findViewById(R.id.tv_crimeType);
            tv_crimeStatus=(TextView)itemView.findViewById(R.id.tv_crimeStatus);
            tv_crimeDate=(TextView)itemView.findViewById(R.id.tv_crimeDate);
            tv_crimeTime=(TextView)itemView.findViewById(R.id.tv_crimeTime);
        }
    }

}
