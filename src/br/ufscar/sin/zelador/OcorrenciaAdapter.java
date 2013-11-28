package br.ufscar.sin.zelador;

import java.util.ArrayList;

import br.ufscar.sin.entidades.Ocorrencia;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OcorrenciaAdapter extends ArrayAdapter<Ocorrencia> {
    private Activity activity;
    private ArrayList<Ocorrencia> listaOcorrencia;
    private static LayoutInflater inflater = null;

    public OcorrenciaAdapter (Activity activity, int textViewResourceId,ArrayList<Ocorrencia> _listaOcorrencia) {
        super(activity, textViewResourceId, _listaOcorrencia);
        try {
            this.activity = activity;
            this.listaOcorrencia = _listaOcorrencia;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return listaOcorrencia.size();
    }

    public Ocorrencia getItem(Ocorrencia position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_categoria;
        public TextView display_detalhamento;
        public ImageView image_status;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.two_list_item, null);
                holder = new ViewHolder();

                holder.display_categoria = (TextView) vi.findViewById(R.id.display_categoria);
                holder.display_detalhamento = (TextView) vi.findViewById(R.id.display_detalhamento);
                holder.image_status = (ImageView) vi.findViewById(R.id.image_status);


                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }



            holder.display_categoria.setText(listaOcorrencia.get(position).getCategoria());
            holder.display_detalhamento.setText(listaOcorrencia.get(position).getDetalhamento());
            if(listaOcorrencia.get(position).getStatus().equals("Criada")) {
            	holder.image_status.setImageResource(R.drawable.status_criada);
            }
            else if(listaOcorrencia.get(position).getStatus().equals("Enviada")) {
            	holder.image_status.setImageResource(R.drawable.status_enviada);
            }
            else if(listaOcorrencia.get(position).getStatus().equals("Atendimento")) {
            	holder.image_status.setImageResource(R.drawable.status_atendimento);
            }
            else if(listaOcorrencia.get(position).getStatus().equals("Resolvida")) {
            	holder.image_status.setImageResource(R.drawable.status_resolvida);
            }
            else if(listaOcorrencia.get(position).getStatus().equals("Nao resolvida")) {
            	holder.image_status.setImageResource(R.drawable.status_nao_resolvida);
            }
            else {
            	holder.image_status.setImageResource(R.drawable.ic_launcher);
            }


        } catch (Exception e) {


        }
        return vi;
    }
}
