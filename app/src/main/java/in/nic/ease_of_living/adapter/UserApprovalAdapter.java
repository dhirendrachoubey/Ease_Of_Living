package in.nic.ease_of_living.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import in.nic.ease_of_living.gp.R;
import in.nic.ease_of_living.models.User;


/**
 * Created by Neha Jain on 7/19/2017.
 */
public class UserApprovalAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    ArrayList<User> Selected_list;


    public UserApprovalAdapter(Context c, ArrayList<User> selected_list) {
        inflater = LayoutInflater.from(c);
        mContext = c;
        this.Selected_list = selected_list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return Selected_list.size();
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
        convertView = inflater.inflate(R.layout.usermanagment_list_item, parent, false);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvUser_mobile = (TextView) convertView.findViewById(R.id.tvUser_mobile);
        TextView tvUser_role = (TextView) convertView.findViewById(R.id.tvUser_role);
        ImageView ivUser_action=(ImageView) convertView.findViewById(R.id.ivUser_action);


        tvUserName.setText(Selected_list.get(position).getEmail_id());
        tvUser_mobile.setText(Selected_list.get(position).getMobile());
        tvUser_role.setText(Selected_list.get(position).getGroup_name());

        if( (Selected_list.get(position).getIsActive() == null) || Selected_list.get(position).getIsActive().equalsIgnoreCase("null"))
            ivUser_action.setImageResource(R.mipmap.icon_red_dot);
        else if(Selected_list.get(position).getIsActive().equalsIgnoreCase("false"))
            ivUser_action.setImageResource(R.mipmap.icon_red_dot);
        else
            ivUser_action.setImageResource(R.mipmap.icon_green_dot);

        return convertView;
    }

}
