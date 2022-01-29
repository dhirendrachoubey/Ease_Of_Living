package in.nic.ease_of_living.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.nic.ease_of_living.gp.R;
import in.nic.ease_of_living.models.SeccPopulation;


/**
 * Created by Chinki Sai on 8/2/2017.
 */
public class SeccPopulationAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    ArrayList<SeccPopulation> alPopUpdated;


    public SeccPopulationAdapter(Context c, ArrayList<SeccPopulation> alPopUpdated) {
        inflater = LayoutInflater.from(c);
        mContext = c;
        this.alPopUpdated = alPopUpdated;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return alPopUpdated.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.population_updated_list_item, parent, false);
        LinearLayout llBg=(LinearLayout)convertView.findViewById(R.id.llBg);
        TextView tvMember_status = (TextView) convertView.findViewById(R.id.tvMember_status);
        tvMember_status.setVisibility(View.GONE);
        TextView tvHouse_no = (TextView) convertView.findViewById(R.id.tvHouse_no);
        TextView tvMember_id = (TextView) convertView.findViewById(R.id.tvMember_id);
        TextView tvName=(TextView) convertView.findViewById(R.id.tvName);
        TextView tvFather_name=(TextView) convertView.findViewById(R.id.tvFather_name);
        TextView tvMother_name=(TextView) convertView.findViewById(R.id.tvMother_name);
        TextView tvDob=(TextView) convertView.findViewById(R.id.tvDob);
        TextView tvYob=(TextView) convertView.findViewById(R.id.tvYob);
        try {

            tvHouse_no.setText(String.valueOf(alPopUpdated.get(position).getHhd_uid()));
            tvMember_id.setText(alPopUpdated.get(position).getMember_sl_no());
            tvName.setText(alPopUpdated.get(position).getName());
            tvFather_name.setText(alPopUpdated.get(position).getFather_name());
            tvMother_name.setText(alPopUpdated.get(position).getMother_name());
            tvDob.setText(alPopUpdated.get(position).getDob());
            //tvYob.setText(alPopUpdated.get(position).getYob());
            /*if((alPopUpdated.get(position).getIs_completed() != null) && (alPopUpdated.get(position).getIs_completed()==1))
            {
                llBg.setBackgroundResource(R.drawable.bg_layout);
            }*/
        }catch (Exception e)
        {

        }

        return convertView;
    }

}
