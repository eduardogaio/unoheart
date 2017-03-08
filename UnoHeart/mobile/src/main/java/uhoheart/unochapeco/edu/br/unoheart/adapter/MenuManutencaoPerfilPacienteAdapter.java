package uhoheart.unochapeco.edu.br.unoheart.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import uhoheart.unochapeco.edu.br.unoheart.R;
import uhoheart.unochapeco.edu.br.unoheart.model.type.TipoOpcoesMenuPerfilPaciente;

/**
 * Created by Eduardo on 21/02/2017.
 */

public class MenuManutencaoPerfilPacienteAdapter extends ArrayAdapter<TipoOpcoesMenuPerfilPaciente> {

    private Typeface mTypeFaceLight;
    private Typeface mTypeFaceRegular;

    public MenuManutencaoPerfilPacienteAdapter(Context context, TipoOpcoesMenuPerfilPaciente[] objects) {
        super(context, 0, objects);

        mTypeFaceLight = Typeface.createFromAsset(context.getAssets(), "OpenSans-Light.ttf");
        mTypeFaceRegular = Typeface.createFromAsset(context.getAssets(), "OpenSans-Regular.ttf");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TipoOpcoesMenuPerfilPaciente c = getItem(position);

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvDesc = (TextView) convertView.findViewById(R.id.tvDesc);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setTypeface(mTypeFaceLight);
        holder.tvDesc.setTypeface(mTypeFaceLight);

        holder.tvName.setText(c.getNome());
        holder.tvDesc.setText(c.getDescricao());

        return convertView;
    }

    private class ViewHolder {

        TextView tvName, tvDesc;
    }
}
