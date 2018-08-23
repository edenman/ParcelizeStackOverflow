package org.coffeetrain.parcelizestackoverflow

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class ParcelizeThingsTest {
  @Test
  fun parcelizeTheThings() {
    roundTrip(MyThing())
  }

  private fun roundTrip(screen: Parcelable) {
    val bundle = Bundle()
    val key = "foo"
    bundle.putParcelable(key, screen)
    val parcel = Parcel.obtain()
    bundle.writeToParcel(parcel, 0)
    parcel.setDataPosition(0) // Reset the parcel for reading.

    val outBundle = parcel.readBundle(screen.javaClass.classLoader)
    assertThat(outBundle).isNotNull()
    val outScreen = outBundle!!.getParcelable<Parcelable>(key)
    assertThat(outScreen).isEqualTo(screen)
  }
}
