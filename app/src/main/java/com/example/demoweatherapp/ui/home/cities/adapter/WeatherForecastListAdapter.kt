package com.example.demoweatherapp.ui.home.cities.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.demoweatherapp.R
import com.example.demoweatherapp.databinding.CitiesForecastLayoutBinding
import com.example.demoweatherapp.databinding.LoaderFooterTransparentGreyBinding
import com.example.demoweatherapp.model.forecast.ForecastCustomizedModel
import com.example.demoweatherapp.ui.home.MainActivity
import com.example.demoweatherapp.ui.home.MainActivityViewModel
import com.example.demoweatherapp.ui.home.cities.WeatherForecastViewModel
import com.example.demoweatherapp.utils.getWeatherIcon
import com.example.demoweatherapp.viewModel.ViewModelProviderFactory
import javax.inject.Inject


class WeatherForecastListAdapter @Inject constructor(val context : MainActivity, viewModelProviderFactory: ViewModelProviderFactory) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items : ArrayList<ForecastCustomizedModel> = ArrayList()

    private val viewModel : WeatherForecastViewModel = ViewModelProviders.of(context, viewModelProviderFactory)
        .get(WeatherForecastViewModel::class.java)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            LOADING_TYPE -> {
                LoadingViewHolder(
                    DataBindingUtil.inflate(LayoutInflater.from(context),
                    R.layout.loader_footer_transparent_grey, parent, false))
            }
            else -> {
                RegularTransactionViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context),
                        R.layout.cities_forecast_layout, parent, false),viewModel,context)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(viewHolder:  RecyclerView.ViewHolder, position: Int) {
        when(viewHolder){
            is RegularTransactionViewHolder -> viewHolder.bind(items[position])
            is LoadingViewHolder -> viewHolder.bind(DOWNLOAD_MORE_DATA)
        }
    }

    /**
     * set data to the adapter.
     * @param dataset to add to the adapter as dataset
     * @return nothing
     */
    fun setData(data : List<ForecastCustomizedModel>){
        items.addAll(data)
        notifyDataSetChanged()
    }

    /**
     * add data to the existing dataset of the adapter. Mostly used for pagination
     * @param dataset to add to the existing dataset
     * @return nothing
     */
    fun addData(data : List<ForecastCustomizedModel>){
        val currentPos = itemCount
        items.addAll(data)
        notifyItemRangeInserted(currentPos, items.size)
    }
    /**
     * clear data from the adapter
     * @return nothing
     */
    fun clearData(){
        items.clear()
        notifyDataSetChanged()
    }

    /**
     * get adapter data
     * @param none
     * @return adapter data set
     */
    fun getData() : ArrayList<ForecastCustomizedModel>{
        return items
    }

    /**
     * remove adapter data
     * @param position of the dataset to remove
     * @return none
     */
    fun removeData(position : Int){
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    /**
     * add data to adapter data set
     * @param data to add
     * @return none
     */
    fun addDataAtPos(data : ForecastCustomizedModel){
        val currentPos = itemCount
        items.add(data)
        notifyItemRangeInserted(currentPos,items.size)
    }

    override fun getItemViewType(position: Int): Int {
        return REGULAR_TYPE
    }

    /** Represents viewholder of the main type view for recycler view
     * @author Aveek
     * @version 1
     * @since Version 1.0
     */
    class RegularTransactionViewHolder(val binding : CitiesForecastLayoutBinding,viewModel: WeatherForecastViewModel, val context: MainActivity)
        : RecyclerView.ViewHolder(binding.root){

        /**
         * set data to xml
         * @param data to set
         * @return none
         */
        fun bind(data: ForecastCustomizedModel) {
            with(binding){
                viewModel = WeatherForecastViewModel().apply {
//                    citiesName.value = data.cityName
                    temperature.set(data.temperature)
                    date.set("${data.dayOfTheWeek}, ${data.dateOfTheMonth} ${data.monthOfTheYear}")
                    weatherType.set(data.weatherType)
                    time.set(data.time)
                    when(data.weatherType){
                        context.getString(R.string.clouds) -> {
                            backgroundColor.set(ContextCompat.getColor(context,R.color.lightBlue))
                            iconDrawable.set(getWeatherIcon(context,context.getString(R.string.clouds)))
                        }
                        context.getString(R.string.rain) ->{
                            backgroundColor.set(ContextCompat.getColor(context,R.color.lightYellow))
                            iconDrawable.set(getWeatherIcon(context,context.getString(R.string.rain)))
                        }
                        context.getString(R.string.clear) ->{
                            backgroundColor.set(ContextCompat.getColor(context,R.color.lightSlateGray))
                            iconDrawable.set(getWeatherIcon(context,context.getString(R.string.clear)))
                        }
                        context.getString(R.string.thunderstorm) ->{
                            backgroundColor.set(ContextCompat.getColor(context,R.color.lightSlateGray))
                            iconDrawable.set(getWeatherIcon(context,context.getString(R.string.thunderstorm)))
                        }
                        else -> {
                            backgroundColor.set(ContextCompat.getColor(context,R.color.indianRed))
                            iconDrawable.set(getWeatherIcon(context,""))
                        }
                    }
                }
            }
        }
    }


    /** Represents viewholder of the Loading type view for recycler view
     * @author Aveek
     * @version 1
     * @since Version 1.0
     */
    class LoadingViewHolder (val binding : LoaderFooterTransparentGreyBinding)
        : RecyclerView.ViewHolder(binding.root){

        /**
         * set data to xml
         * @param visibility to toggle visibility
         * @return none
         */
        fun bind(visibility : Int){
            binding.progressBar.visibility = if (visibility == DOWNLOAD_MORE_DATA) View.VISIBLE else View.GONE
        }
    }
}