package io.computenode.cyfra.samples.New

import io.computenode.cyfra
import io.computenode.cyfra.*
import io.computenode.cyfra.dsl.Algebra.{*, given}
import io.computenode.cyfra.dsl.Functions.*
import io.computenode.cyfra.dsl.GSeq
import io.computenode.cyfra.dsl.Value.*
import io.computenode.cyfra.foton.animation.AnimatedFunctionRenderer.Parameters
import io.computenode.cyfra.foton.animation.AnimationFunctions.*
import io.computenode.cyfra.foton.animation.{AnimatedFunction, AnimatedFunctionRenderer}
import io.computenode.cyfra.utility.Color.*
import io.computenode.cyfra.utility.Math3D.*

import scala.concurrent.duration.DurationInt
import java.nio.file.Paths

object Full_animation:
  @main
  def parametric_1() =
    def parametric(uv: Vec2[Float32])(using AnimationInstant): Float32 =
        val t = smooth(from = -0.605f, to = 0.780f, duration = 7.seconds)
        GSeq.gen(uv, next = v => {
        val dx = -v.x * v.x + v.x * t + v.y
        val dy = v.x * v.x - v.y * v.y - t * t - v.x * v.y + v.y * t - v.x + v.y
        (dx, dy)
        }).limit(45).map(length).fold(0.0f, _ + _) // Accumulate values correctly

  
    def parametricColor(uv: Vec2[Float32])(using AnimationInstant): Vec4[Float32] =
        val f = min(1f, parametric(uv) / 100f) // Normalize value for color interpolation
        val color = interpolate(InterpolationThemes.Blue, f)
        (
          color.r,
          color.g,
          color.b,
          1.0f
        )

    val animatedParametric = AnimatedFunction.fromCoord(parametricColor, 4.seconds)
    
    val renderer = AnimatedFunctionRenderer(Parameters(720, 720, 30))
    renderer.renderFramesToDir(animatedParametric, Paths.get("Spiral"))
