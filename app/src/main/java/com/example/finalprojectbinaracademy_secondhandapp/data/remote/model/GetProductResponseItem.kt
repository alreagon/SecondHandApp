package com.example.finalprojectbinaracademy_secondhandapp.data.remote.model


import android.os.Parcelable
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize
import java.util.*

//@Entity(tableName = "getProductHome")
@TypeConverters(CategoryTypeConverter::class)
data class GetProductResponseItem(

    @SerializedName("base_price")
    val basePrice: Int,
    @SerializedName("Categories")
    val categories: List<CategoryX>,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("description")
    val description: String,

    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @SerializedName("image_name")
    val imageName: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int
)

//class LanguageConverter {
//    @TypeConverter
//    fun storedStringToLanguages(value: String): CountryLangs {
//        val langs: List<String> = Arrays.asList(value.split("\\s*,\\s*".toRegex()).toTypedArray())
//        return CountryLangs(langs)
//    }
//
//    @TypeConverter
//    fun languagesToStoredString(cl: CountryLangs): String {
//        var value = ""
//        for (lang in cl.getCountryLangs()) value += "$lang,"
//        return value
//    }
//}

class CategoryTypeConverter {
    private val gson = Gson()
    @TypeConverter
    fun stringToList(data: String?): List<CategoryX> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<CategoryX>>() {

        }.type

        return gson.fromJson<List<CategoryX>>(data, listType)
    }

    @TypeConverter
    fun listToString(someObjects: List<CategoryX>): String {
        return gson.toJson(someObjects)
    }
}

@Parcelize
data class CategoryX(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
) : Parcelable

