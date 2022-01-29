package in.nic.ease_of_living.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.nic.ease_of_living.gp.R;
import in.nic.ease_of_living.models.HouseholdEol;


/**
 * Created by Chinki Sai on 1/06/18.
 */

public class HouseholdEolPaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private List<HouseholdEol> alHhdEol;
    private Context context;

    private boolean isLoadingAdded = false;

    public HouseholdEolPaginationAdapter(Context context) {
        this.context = context;
        alHhdEol = new ArrayList<>();
    }

    public List<HouseholdEol> getlists() {
        return alHhdEol;
    }

    public void setlists(List<HouseholdEol> lists) {
        this.alHhdEol = lists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.layout_household_eol_header_list_item, parent, false);
        viewHolder = new PopVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        HouseholdEol h = alHhdEol.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                PopVH popVH = (PopVH) holder;

                try {

                    String strHouseholdStatus = "";

                    popVH.tvHouseholdIdData.setText(context.getString(R.string.household) + " : " + String.valueOf(h.getHhd_uid()));
                    if(h.getUncovered_reason().equalsIgnoreCase("not_started"))
                    {
                        strHouseholdStatus = context.getString(R.string.hhd_not_started);
                        popVH.tvHouseholdIdData.setTextColor(ContextCompat.getColor(context, R.color.black));
                    }
                    else
                    if(h.getIs_verified() == 1)
                    {
                        strHouseholdStatus = context.getString(R.string.hhd_verified);
                        popVH.tvHouseholdIdData.setTextColor(ContextCompat.getColor(context, R.color.red));
                    }
                    else if(h.getIs_uploaded() == 1)
                    {
                        strHouseholdStatus = context.getString(R.string.hhd_uploaded);
                        popVH.tvHouseholdIdData.setTextColor(ContextCompat.getColor(context, R.color.red));
                    }
                    else if(h.getIs_synchronized() == 1)
                    {
                        strHouseholdStatus = context.getString(R.string.hhd_send_to_server);
                        popVH.tvHouseholdIdData.setTextColor(ContextCompat.getColor(context, R.color.red));
                    }
                    else if(h.getIs_completed() == 1)
                    {
                        strHouseholdStatus = context.getString(R.string.hhd_completed);
                        popVH.tvHouseholdIdData.setTextColor(ContextCompat.getColor(context, R.color.red));
                    }
                    else if(h.getIs_completed() == 0){
                        strHouseholdStatus = context.getString(R.string.hhd_draft);
                        popVH.tvHouseholdIdData.setTextColor(ContextCompat.getColor(context, R.color.red));
                    }

                    popVH.tvHouseholdStatusData.setText(strHouseholdStatus);

                } catch (Exception e) {

                }
                break;
            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return alHhdEol == null ? 0 : alHhdEol.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == alHhdEol.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    /*
   Helpers
   _________________________________________________________________________________________________
    */

    /* SeccPopulation */
    public void add(HouseholdEol mc) {
        alHhdEol.add(mc);
        notifyItemInserted(alHhdEol.size() - 1);
    }

    public void addAll(List<HouseholdEol> mcList) {
        for (HouseholdEol mc : mcList) {
            add(mc);
        }
    }

    public void remove(HouseholdEol city) {
        int position = alHhdEol.indexOf(city);
        if (position > -1) {
            alHhdEol.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new HouseholdEol());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = alHhdEol.size() - 1;
        HouseholdEol item = getItem(position);

        if (item != null) {
            alHhdEol.remove(position);
            notifyItemRemoved(position);
        }
    }

    public HouseholdEol getItem(int position) {
        return alHhdEol.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    protected class PopVH extends RecyclerView.ViewHolder {
        public TextView tvHouseholdIdData, tvHouseholdStatusData;

        public PopVH(View convertView) {
            super(convertView);
            tvHouseholdIdData = convertView.findViewById(R.id.tvHouseholdIdData);
            tvHouseholdStatusData = convertView.findViewById(R.id.tvHouseholdStatusData);

        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


}
