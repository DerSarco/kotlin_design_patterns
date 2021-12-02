package creationalPatterns

import org.assertj.core.api.Assertions
import org.junit.Test

interface DataSource


class DataBasesDataSource: DataSource

class NetworkDataSource: DataSource


abstract class DataSourceFactory{
    abstract fun makeDataSource(): DataSource

    companion object {
        inline fun <reified T: DataSource> createFactory(): DataSourceFactory =
            when(T::class){
                DataBasesDataSource::class -> DatabaseFactory()
                NetworkDataSource::class -> NetworkFactory()
                else -> throw IllegalArgumentException()
            }
    }

}

class NetworkFactory: DataSourceFactory(){
    override fun makeDataSource(): DataSource = NetworkDataSource()
}

class DatabaseFactory: DataSourceFactory(){
    override fun makeDataSource(): DataSource = DataBasesDataSource()
}

class AbstractFactoryTest{
    @Test
    fun abstractFactoryTest(){
        val dataSourceFactory = DataSourceFactory.createFactory<DataBasesDataSource>()
        val dataSource = dataSourceFactory.makeDataSource()
        println("Created datasource: $dataSource")

        Assertions.assertThat(dataSource).isInstanceOf(DataBasesDataSource::class.java)
    }
}