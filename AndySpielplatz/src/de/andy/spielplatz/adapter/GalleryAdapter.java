package de.andy.spielplatz.adapter;

import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import de.andy.spielplatz.R;
import de.andy.spielplatz.dj.Album;

public class GalleryAdapter extends BaseAdapter
{
	private List<Album> allAlbums;
	private Context context;
	private int mGalleryItemBackground;

	public GalleryAdapter(List<Album> albums, Context c)
	{
		this.allAlbums = albums;
		this.context = c;

		TypedArray attr = c.obtainStyledAttributes(R.styleable.GalleryBackground);
		this.mGalleryItemBackground = attr.getResourceId(R.styleable.GalleryBackground_android_galleryItemBackground, 0);
		attr.recycle();

	}

	@Override
	public int getCount()
	{
		return this.allAlbums.size();
	}

	@Override
	public Object getItem(int i)
	{
		return this.allAlbums.get(i);
	}

	@Override
	public long getItemId(int i)
	{
		return i;
	}

	@Override
	public View getView(int i, View convertView, ViewGroup parent)
	{
		ImageView imageView = new ImageView(this.context);
		imageView.setLayoutParams(new Gallery.LayoutParams(100, 100));
		imageView.setBackgroundResource(this.mGalleryItemBackground);

		Album album = this.allAlbums.get(i);
		if (album != null)
			if (album.getAlbumArtUri() == null)
				imageView.setImageResource(R.drawable.art_not_found);
			else
				imageView.setImageURI(Uri.parse(album.getAlbumArtUri()));

		return imageView;
	}
}
