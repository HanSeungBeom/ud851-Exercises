package bumbums.t02mysolution;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hanseungbeom on 2018. 4. 27..
 */

public class GitAdapter extends RecyclerView.Adapter<GitAdapter.GitViewHolder> {
    private Context mContext;
    private ArrayList<GitRep> gitReps;

    public GitAdapter(Context context, ArrayList<GitRep> result) {
        mContext = context;
        this.gitReps = result;
    }

    @Override
    public GitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false);
        return new GitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GitViewHolder holder, int position) {
        GitRep gitRep = gitReps.get(position);
        holder.name.setText(gitRep.getName());
        Picasso.get().load(gitRep.getAvatarUrl()).into(holder.profile);
    }

    @Override
    public int getItemCount() {
        return gitReps.size();
    }

    public class GitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        ImageView profile;

        public GitViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.tv_name);
            profile = (ImageView)itemView.findViewById(R.id.iv_profile);
        }
        @Override
        public void onClick(View v) {

        }
    }
}