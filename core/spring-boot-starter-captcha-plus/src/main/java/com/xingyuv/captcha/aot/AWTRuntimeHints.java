package com.xingyuv.captcha.aot;

import org.springframework.aot.hint.*;

import java.util.Collections;
import java.util.List;

public class AWTRuntimeHints implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        hints.reflection()
                .registerType(TypeReference.of("sun.java2d.marlin.DMarlinRenderingEngine"), MemberCategory.values())
                .registerType(TypeReference.of("java.awt.Color"), MemberCategory.values())
                .registerType(TypeReference.of("javax.imageio.spi.ImageReaderSpi"), MemberCategory.values())
                .registerType(TypeReference.of("javax.imageio.spi.ImageReaderWriterSpi"), MemberCategory.values())
                .registerType(TypeReference.of("javax.imageio.spi.ImageWriterSpi"), MemberCategory.values())
                .registerType(TypeReference.of("javax.imageio.plugins.tiff.BaselineTIFFTagSet"), MemberCategory.values())
                .registerType(TypeReference.of("javax.imageio.plugins.tiff.ExifGPSTagSet"), MemberCategory.values())
                .registerType(TypeReference.of("javax.imageio.plugins.tiff.ExifInteroperabilityTagSet"), MemberCategory.values())
                .registerType(TypeReference.of("javax.imageio.plugins.tiff.ExifParentTIFFTagSet"), MemberCategory.values())
                .registerType(TypeReference.of("javax.imageio.plugins.tiff.ExifTIFFTagSet"), MemberCategory.values())
                .registerType(TypeReference.of("javax.imageio.plugins.tiff.FaxTIFFTagSet"), MemberCategory.values())
                .registerType(TypeReference.of("javax.imageio.plugins.tiff.GeoTIFFTagSet"), MemberCategory.values())
                .registerType(TypeReference.of("javax.imageio.plugins.tiff.TIFFTagSet"), MemberCategory.values())
                .registerType(TypeReference.of("sun.java2d.loops.OpaqueCopyAnyToArgb"), MemberCategory.values())
                .registerType(TypeReference.of("sun.java2d.loops.OpaqueCopyArgbToAny"), MemberCategory.values())
                .registerType(TypeReference.of("sun.java2d.loops.SetDrawLineANY"), MemberCategory.values())
                .registerType(TypeReference.of("sun.java2d.loops.SetDrawPathANY"), MemberCategory.values())
                .registerType(TypeReference.of("sun.java2d.loops.SetDrawPolygonsANY"), MemberCategory.values())
                .registerType(TypeReference.of("sun.java2d.loops.SetDrawRectANY"), MemberCategory.values())
                .registerType(TypeReference.of("sun.java2d.loops.SetFillPathANY"), MemberCategory.values())
                .registerType(TypeReference.of("sun.java2d.loops.SetFillRectANY"), MemberCategory.values())
                .registerType(TypeReference.of("sun.java2d.loops.SetFillSpansANY"), MemberCategory.values())

                .registerType(TypeReference.of("sun.java2d.marlin.DMarlinRenderingEngine"), this::buildDMarlinRenderingEngine)
        ;

        hints.jni()
                .registerType(TypeReference.of("com.sun.imageio.plugins.jpeg.JPEGImageReader"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("com.sun.imageio.plugins.jpeg.JPEGImageWriter"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.AlphaComposite"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.Color"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.Composite"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.GraphicsEnvironment"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.Rectangle"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.Transparency"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.color.CMMException"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.color.ColorSpace"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.color.ICC_ColorSpace"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.color.ICC_Profile"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.color.ICC_ProfileGray"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.color.ICC_ProfileRGB"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.geom.AffineTransform"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.geom.GeneralPath"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.geom.Path2D"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.geom.Path2D$Float"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.geom.Point2D$Float"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.geom.Rectangle2D$Float"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.image.AffineTransformOp"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.image.BandedSampleModel"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.image.BufferedImage"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.image.ColorModel"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.image.ComponentColorModel"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.image.ComponentSampleModel"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.image.ConvolveOp"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.image.DirectColorModel"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.image.IndexColorModel"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.image.Kernel"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.image.MultiPixelPackedSampleModel"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.image.PackedColorModel"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.image.PixelInterleavedSampleModel"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.image.Raster"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.image.SampleModel"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("java.awt.image.SinglePixelPackedSampleModel"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("javax.imageio.IIOException"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("javax.imageio.plugins.jpeg.JPEGHuffmanTable"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("javax.imageio.plugins.jpeg.JPEGQTable"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.awt.SunHints"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.awt.image.BufImgSurfaceData"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.awt.image.BufImgSurfaceData$ICMColorData"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.awt.image.ByteBandedRaster"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.awt.image.ByteComponentRaster"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.awt.image.ByteInterleavedRaster"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.awt.image.BytePackedRaster"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.awt.image.DataBufferNative"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.awt.image.GifImageDecoder"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.awt.image.ImageRepresentation"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.awt.image.ImagingLib"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.awt.image.IntegerComponentRaster"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.awt.image.IntegerInterleavedRaster"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.awt.image.ShortBandedRaster"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.awt.image.ShortComponentRaster"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.awt.image.ShortInterleavedRaster"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.awt.image.SunWritableRaster"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.awt.image.WritableRasterNative"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.font.CharToGlyphMapper"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.font.Font2D"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.font.FontConfigManager"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.font.FontConfigManager$FcCompFont"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.font.FontConfigManager$FontConfigFont"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.font.FontConfigManager$FontConfigInfo"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.font.FontManagerNativeLibrary"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.font.FontStrike"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.font.FontUtilities"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.font.FreetypeFontScaler"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.font.GlyphLayout"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.font.GlyphLayout$EngineRecord"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.font.GlyphLayout$GVData"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.font.GlyphLayout$LayoutEngine"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.font.GlyphLayout$LayoutEngineFactory"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.font.GlyphLayout$LayoutEngineKey"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.font.GlyphLayout$SDCache"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.font.GlyphLayout$SDCache$SDKey"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.font.GlyphList"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.font.PhysicalStrike"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.font.StrikeMetrics"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.font.TrueTypeFont"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.font.Type1Font"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.DefaultDisposerRecord"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.Disposer"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.InvalidPipeException"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.NullSurfaceData"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.SunGraphics2D"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.SurfaceData"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.cmm.lcms.LCMS"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.cmm.lcms.LCMSImageLayout"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.cmm.lcms.LCMSProfile"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.cmm.lcms.LCMSTransform"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.loops.Blit"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.loops.BlitBg"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.loops.CompositeType"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.loops.DrawGlyphList"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.loops.DrawGlyphListAA"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.loops.DrawGlyphListLCD"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.loops.DrawLine"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.loops.DrawParallelogram"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.loops.DrawPath"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.loops.DrawPolygons"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.loops.DrawRect"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.loops.FillParallelogram"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.loops.FillPath"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.loops.FillRect"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.loops.FillSpans"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.loops.GraphicsPrimitive"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.loops.GraphicsPrimitiveMgr"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.loops.MaskBlit"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.loops.MaskFill"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.loops.ScaledBlit"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.loops.SurfaceType"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.loops.TransformHelper"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.loops.XORComposite"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.pipe.BufferedMaskBlit"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.pipe.GlyphListPipe"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.pipe.Region"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.pipe.RegionIterator"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.pipe.ShapeSpanIterator"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.pipe.SpanClipRenderer"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.pipe.SpanIterator"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.java2d.pipe.ValidatePipe"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)
                .registerType(TypeReference.of("sun.management.VMManagementImpl"), MemberCategory.DECLARED_FIELDS, MemberCategory.INVOKE_DECLARED_METHODS)

                .registerType(TypeReference.of("sun.awt.image.BufImgSurfaceData$ICMColorData"), this::buildICMColorData)
                .registerType(TypeReference.of("sun.font.StrikeMetrics"), this::buildStrikeMetrics)
                .registerType(TypeReference.of("sun.java2d.loops.Blit"), this::buildBlit)
                .registerType(TypeReference.of("sun.java2d.loops.BlitBg"), this::buildBlit)
                .registerType(TypeReference.of("sun.java2d.loops.DrawGlyphList"), this::buildBlit)
                .registerType(TypeReference.of("sun.java2d.loops.DrawGlyphListAA"), this::buildBlit)
                .registerType(TypeReference.of("sun.java2d.loops.DrawGlyphListLCD"), this::buildBlit)
                .registerType(TypeReference.of("sun.java2d.loops.DrawLine"), this::buildBlit)
                .registerType(TypeReference.of("sun.java2d.loops.DrawParallelogram"), this::buildBlit)
                .registerType(TypeReference.of("sun.java2d.loops.DrawPath"), this::buildBlit)
                .registerType(TypeReference.of("sun.java2d.loops.DrawPolygons"), this::buildBlit)
                .registerType(TypeReference.of("sun.java2d.loops.DrawRect"), this::buildBlit)
                .registerType(TypeReference.of("sun.java2d.loops.FillParallelogram"), this::buildBlit)
                .registerType(TypeReference.of("sun.java2d.loops.FillPath"), this::buildBlit)
                .registerType(TypeReference.of("sun.java2d.loops.FillRect"), this::buildBlit)
                .registerType(TypeReference.of("sun.java2d.loops.FillSpans"), this::buildBlit)
                .registerType(TypeReference.of("sun.java2d.loops.MaskBlit"), this::buildBlit)
                .registerType(TypeReference.of("sun.java2d.loops.MaskFill"), this::buildBlit)
                .registerType(TypeReference.of("sun.java2d.loops.ScaledBlit"), this::buildBlit)
                .registerType(TypeReference.of("sun.java2d.loops.TransformHelper"), this::buildBlit)
                .registerType(TypeReference.of("java.awt.geom.GeneralPath"), this::buildGeneralPath)
                .registerType(TypeReference.of("java.awt.geom.Point2D$Float"), this::buildPoint2D)
                .registerType(TypeReference.of("java.awt.geom.Rectangle2D$Float"), this::buildRectangle2D)
                .registerType(TypeReference.of("sun.font.FontConfigManager$FontConfigFont"), this::buildFontConfigFont)
        ;

        hints.resources()
                .registerPattern(".*/iio-plugin.*properties$")
                .registerPattern(".*/.*pf$")
                .registerType(TypeReference.of("sun.awt.resources.awt"));
    }

    private void buildDMarlinRenderingEngine(TypeHint.Builder builder) {
        builder.withMethod("<init>", List.of(TypeReference.of(long.class)), ExecutableMode.INVOKE);
    }

    private void buildICMColorData(TypeHint.Builder builder) {
        builder.withMethod("<init>", List.of(TypeReference.of(long.class)), ExecutableMode.INVOKE);
    }

    private void buildStrikeMetrics(TypeHint.Builder builder) {
        builder.withMethod("<init>", List.of(TypeReference.of(float.class), TypeReference.of(float.class),
                TypeReference.of(float.class), TypeReference.of(float.class), TypeReference.of(float.class),
                TypeReference.of(float.class), TypeReference.of(float.class), TypeReference.of(float.class),
                TypeReference.of(float.class), TypeReference.of(float.class)), ExecutableMode.INVOKE);
    }

    private void buildBlit(TypeHint.Builder builder) {
        builder.withMethod("<init>", List.of(TypeReference.of(long.class), TypeReference.of("sun.java2d.loops.SurfaceType"),
                        TypeReference.of("sun.java2d.loops.CompositeType"), TypeReference.of("sun.java2d.loops.SurfaceType")),
                ExecutableMode.INVOKE);
    }

    private void buildGeneralPath(TypeHint.Builder builder) {
        builder.withMethod("<init>", List.of(TypeReference.of(int.class), TypeReference.of(byte[].class),
                                TypeReference.of(int.class), TypeReference.of(float[].class), TypeReference.of(int.class)),
                        ExecutableMode.INVOKE)
                .withMethod("<init>", Collections.emptyList(), ExecutableMode.INVOKE);
    }

    private void buildPoint2D(TypeHint.Builder builder) {
        builder.withMethod("<init>", List.of(TypeReference.of(float.class), TypeReference.of(float.class)),
                ExecutableMode.INVOKE);
    }

    private void buildRectangle2D(TypeHint.Builder builder) {
        builder.withMethod("<init>", Collections.emptyList(), ExecutableMode.INVOKE)
                .withMethod("<init>", List.of(TypeReference.of(float.class), TypeReference.of(float.class),
                        TypeReference.of(float.class), TypeReference.of(float.class)), ExecutableMode.INVOKE);
    }

    private void buildFontConfigFont(TypeHint.Builder builder) {
        builder.withMethod("<init>", Collections.emptyList(), ExecutableMode.INVOKE);
    }
}
