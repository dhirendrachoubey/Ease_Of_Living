package in.nic.ease_of_living.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.nic.ease_of_living.gp.R;
import in.nic.ease_of_living.models.SeccPopulation;
import in.nic.ease_of_living.utils.Support;


/**
 * Created by Chinki Sai on 1/06/18.
 */

public class SeccPopPaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private List<SeccPopulation> alSeccPop;
    private Context context;

    private boolean isLoadingAdded = false;
    public String TAG = "SeccPopPaginationAdapter";

    public SeccPopPaginationAdapter(Context context) {
        this.context = context;
        alSeccPop = new ArrayList<>();
    }

    public List<SeccPopulation> getlists() {
        return alSeccPop;
    }

    public void setlists(List<SeccPopulation> lists) {
        this.alSeccPop = lists;
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

        SeccPopulation pop = alSeccPop.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                PopVH popVH = (PopVH) holder;

                try {

                    if (pop.getName() != null)
                        popVH.tvName.setText(pop.getName());

                    if ( pop.getFather_name()!= null && !Support.isWhite_space(pop.getFather_name()))
                        popVH.tvFather_name.setText(pop.getFather_name());
                    Log.d(TAG, "onBindViewHolder:Ahl_tin "+pop.getAhl_tin());

                    if (pop.getMother_name() != null && !Support.isWhite_space(pop.getFather_name()))
                        popVH.tvMother_name.setText(pop.getMother_name());

                    if (pop.getDob() != null)
                        popVH.tvDob.setText(pop.getDob());

                  /*  if (pop.getMember_status() != null)
                        popVH.tvMember_status.setText(pop.getMember_status());

                 */   if (pop.getHhd_uid() != null)
                        popVH.tvHouse_no.setText(pop.getHhd_uid());

                    //strGender=AESHelper.decipher(k,pop.getGender_code());
                    String strGender = pop.getGenderid();
                    if ((strGender != null) && !(strGender.trim().isEmpty())) {
                        if ((strGender.equalsIgnoreCase("1")))
                            popVH.tvGender.setText("Male");
                        if ((strGender.equalsIgnoreCase("2")))
                            popVH.tvGender.setText("Female");
                        if ((strGender.equalsIgnoreCase("3")))
                            popVH.tvGender.setText("Transgender");
                    }

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
        return alSeccPop == null ? 0 : alSeccPop.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == alSeccPop.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    /*
   Helpers
   _________________________________________________________________________________________________
    */

    /* SeccPopulation */
    public void add(SeccPopulation mc) {
        alSeccPop.add(mc);
        notifyItemInserted(alSeccPop.size() - 1);
    }

    public void addAll(List<SeccPopulation> mcList) {
        for (SeccPopulation mc : mcList) {
            add(mc);
        }
    }

    public void remove(SeccPopulation city) {
        int position = alSeccPop.indexOf(city);
        if (position > -1) {
            alSeccPop.remove(position);
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
        add(new SeccPopulation());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = alSeccPop.size() - 1;
        SeccPopulation item = getItem(position);

        if (item != null) {
            alSeccPop.remove(position);
            notifyItemRemoved(position);
        }
    }

    public SeccPopulation getItem(int position) {
        return alSeccPop.get(position);
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

            llAddress.setVisibility(View.GONE);
            llDob.setVisibility(View.VISIBLE);
            llGender.setVisibility(View.VISIBLE);
            llHouseUid.setVisibility(View.GONE);
            llMemberStatus.setVisibility(View.VISIBLE);
        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


}
