package com.helldeadshot.navcast.data.api

import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.util.MapTileIndex

object OSMTileService {

    val MAPNIK = XYTileSource(
        "Mapnik",
        0, 19, 256, ".png",
        arrayOf(
            "https://a.tile.openstreetmap.org/",
            "https://b.tile.openstreetmap.org/",
            "https://c.tile.openstreetmap.org/"
        )
    )

    val CYCLE_MAP = XYTileSource(
        "CycleMap",
        0, 18, 256, ".png",
        arrayOf("https://a.tile.thunderforest.com/cycle/"),
        "© OpenCycleMap, © OpenStreetMap contributors"
    )

    val TRANSPORT_MAP = XYTileSource(
        "TransportMap",
        0, 18, 256, ".png",
        arrayOf("https://a.tile.thunderforest.com/transport/"),
        "© Thunderforest, © OpenStreetMap contributors"
    )
}
