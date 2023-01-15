//package io.shaded.campfire.configuration
//
//import io.r2dbc.spi.ConnectionFactory
//import io.r2dbc.spi.ConnectionFactoryOptions
//import org.springframework.boot.r2dbc.ConnectionFactoryBuilder
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.context.annotation.Primary
//import org.springframework.core.io.ClassPathResource
//import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
//import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator
//import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
//import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator
//
//@Configuration
//class PostgresConfiguration : AbstractR2dbcConfiguration() {
//  @Bean
//  @Primary
//  override fun connectionFactory(): ConnectionFactory =
//    ConnectionFactoryBuilder.withOptions(
//      ConnectionFactoryOptions.builder()
//        .option(ConnectionFactoryOptions.DRIVER, "postgresql")
//        .option(ConnectionFactoryOptions.HOST, "localhost")
//        .option(ConnectionFactoryOptions.PORT, 5432)
//        .option(ConnectionFactoryOptions.USER, "campfire")
//        .option(ConnectionFactoryOptions.PASSWORD, "campfire")
//        .option(ConnectionFactoryOptions.DATABASE, "campfire")
//    ).build()
//
//  @Bean
//  fun initializer(connectionFactory: ConnectionFactory):
//    ConnectionFactoryInitializer {
//    val initializer = ConnectionFactoryInitializer()
//    initializer.setConnectionFactory(connectionFactory)
//    val populator = CompositeDatabasePopulator()
//    populator.addPopulators(ResourceDatabasePopulator(ClassPathResource("schema.sql")))
//    initializer.setDatabasePopulator(populator)
//    return initializer
//  }
//}
