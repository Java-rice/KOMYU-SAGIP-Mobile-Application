import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobile.komyusagip.R

class CrimeReportAdapter(private var crimeReports: List<CrimeReport>) :
    RecyclerView.Adapter<CrimeReportAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val crimeTypeTextView: TextView = itemView.findViewById(R.id.crimeTypeTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_crime_report, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val crimeReport = crimeReports[position]
        holder.crimeTypeTextView.text = crimeReport.crimeType
        holder.descriptionTextView.text = crimeReport.description
    }

    override fun getItemCount(): Int {
        return crimeReports.size
    }

    // Method to set new data to the adapter
    fun setData(newCrimeReports: List<CrimeReport>) {
        crimeReports = newCrimeReports
        notifyDataSetChanged()
    }
}
