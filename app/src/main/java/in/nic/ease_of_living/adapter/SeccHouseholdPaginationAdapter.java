package in.nic.ease_of_living.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.nic.ease_of_living.gp.R;
import in.nic.ease_of_living.models.SeccHousehold;
import in.nic.ease_of_living.utils.Support;


/**
 * Created by Chinki Sai on 1/06/18.
 */

public class SeccHouseholdPaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private List<SeccHousehold> alSeccHhd;
    private Context context;

    private boolean isLoadingAdded = false;

    public SeccHouseholdPaginationAdapter(Context context) {
        this.context = context;
        alSeccHhd = new ArrayList<>();
    }

    public List<SeccHousehold> getlists() {
        return alSeccHhd;
    }

    public void setlists(List<SeccHousehold> lists) {
        this.alSeccHhd = lists;
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
        View v1 = inflater.inflate(R.layout.population_list_item, parent, false);
        viewHolder = new PopVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        SeccHousehold h = alSeccHhd.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                PopVH popVH = (PopVH) holder;

                try {

                    if (h.getHead_name() != null)
                        popVH.tvName.setText(h.getHead_name());

                    if ( h.getHead_fathername()!= null && !Support.isWhite_space(h.getHead_fathername()))
                        popVH.tvFather_name.setText(h.getHead_fathername());

                    if (h.getHead_mothername() != null && !Support.isWhite_space(h.getHead_mothername()))
                        popVH.tvMother_name.setText(h.getHead_mothername());

                    if (h.getHhd_uid() != null)
                        popVH.tvHouse_no.setText(h.getHhd_uid());

                    String strAddress = "";
                    if((h.getAddressline1() != null) && !Support.isWhite_space(h.getAddressline1()))
                    {
                        strAddress += h.getAddressline1();
                        strAddress += "\n";
                    }

                    if((h.getAddressline2() != null) && !Support.isWhite_space(h.getAddressline2()))
                    {
                        strAddress += h.getAddressline2();
                        strAddress += "\n";
                    }

                    if((h.getAddressline3() != null) && !Support.isWhite_space(h.getAddressline3()))
                        strAddress += h.getAddressline3();

                    popVH.tvAddress.setText(strAddress);

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
        return alSeccHhd == null ? 0 : alSeccHhd.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == alSeccHhd.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    /*
   Helpers
   _________________________________________________________________________________________________
    */

    /* SeccPopulation */
    public void add(SeccHousehold mc) {
        alSeccHhd.add(mc);
        notifyItemInserted(alSeccHhd.size() - 1);
    }

    public void addAll(List<SeccHousehold> mcList) {
        for (SeccHousehold mc : mcList) {
            add(mc);
        }
    }

    public void remove(SeccHousehold city) {
        int position = alSeccHhd.indexOf(city);
        if (position > -1) {
            alSeccHhd.remove(position);
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
        add(new SeccHousehold());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = alSeccHhd.size() - 1;
        SeccHousehold item = getItem(position);

        if (item != null) {
            alSeccHhd.remove(position);
            notifyItemRemoved(position);
        }
    }

    public SeccHousehold getItem(int position) {
        return alSeccHhd.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    protected class PopVH extends RecyclerView.ViewHolder {
        public TextView tvName, tvFather_name, tvMother_name,tvDob,tvGender,tvMember_status,tvHouse_no, tvAddress;
        public LinearLayout llAddress, llDob, llGender, llMemberStatus, llHouseUid;

        public PopVH(View convertView) {
            super(convertView);
            tvName = (TextView) convertView.findViewById(R.id.tvName);
            tvFather_name=(TextView) convertView.findViewById(R.id.tvFather_name);
            tvMother_name=(TextView) convertView.findViewById(R.id.tvMother_name);
            tvDob=(TextView) convertView.findViewById(R.id.tvDOB);
            tvGender=(TextView) convertView.findViewById(R.id.tvGender);
            tvMember_status=(TextView) convertView.findViewById(R.id.tvMember_status);
            tvHouse_no=(TextView) convertView.findViewById(R.id.tvHouse_no);
            tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
            llAddress = (LinearLayout) convertView.findViewById(R.id.llAddress);
            llDob = (LinearLayout) convertView.findViewById(R.id.llDob);
            llGender = (LinearLayout) convertView.findViewById(R.id.llGender);
            llMemberStatus = (LinearLayout) convertView.findViewById(R.id.llMemberStatus);
            llHouseUid = (LinearLayout) convertView.findViewById(R.id.llHouseUid);

            llAddress.setVisibility(View.VISIBLE);
            llDob.setVisibility(View.GONE);
            llGender.setVisibility(View.GONE);
            llHouseUid.setVisibility(View.GONE);
            llMemberStatus.setVisibility(View.GONE);
        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


}
